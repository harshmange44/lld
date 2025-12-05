package org.hrsh.linkedin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class SearchIndex {
    private final Map<String, List<Member>> memberNamesMap;
    private final Map<String, List<Group>> groupNamesMap;
    private final Map<String, List<Company>> companyNamesMap;
    private final Map<String, List<Post>> postWordsMap;

    public SearchIndex() {
        this.memberNamesMap = new ConcurrentHashMap<>();
        this.groupNamesMap = new ConcurrentHashMap<>();
        this.companyNamesMap = new ConcurrentHashMap<>();
        this.postWordsMap = new ConcurrentHashMap<>();
    }

    public synchronized void addMemberName(Member member) {
        if (member == null || member.getName() == null) {
            return;
        }

        String name = member.getName().toLowerCase();
        memberNamesMap.computeIfAbsent(name, k -> new CopyOnWriteArrayList<>()).add(member);
    }

    public synchronized void addGroupName(Group group) {
        if (group == null || group.getName() == null) {
            return;
        }

        String name = group.getName().toLowerCase();
        groupNamesMap.computeIfAbsent(name, k -> new CopyOnWriteArrayList<>()).add(group);
    }

    public synchronized void addCompanyName(Company company) {
        if (company == null || company.getName() == null) {
            return;
        }

        String name = company.getName().toLowerCase();
        companyNamesMap.computeIfAbsent(name, k -> new CopyOnWriteArrayList<>()).add(company);
    }

    public synchronized void addPostWords(Post post) {
        if (post == null || post.getText() == null) {
            return;
        }

        // Extract words from post text (simple word splitting)
        String[] words = post.getText().toLowerCase().split("\\s+");
        for (String word : words) {
            if (word.length() > 2) { // Ignore very short words
                postWordsMap.computeIfAbsent(word, k -> new CopyOnWriteArrayList<>()).add(post);
            }
        }
    }

    public List<Member> searchMembers(String memberName) {
        if (memberName == null || memberName.isEmpty()) {
            return Collections.emptyList();
        }

        String searchKey = memberName.toLowerCase();
        List<Member> exactMatches = memberNamesMap.getOrDefault(searchKey, Collections.emptyList());

        // Also search for partial matches
        List<Member> partialMatches = memberNamesMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(searchKey))
                .flatMap(entry -> entry.getValue().stream())
                .distinct()
                .collect(Collectors.toList());

        // Combine and deduplicate
        Set<Member> allMatches = new LinkedHashSet<>(exactMatches);
        allMatches.addAll(partialMatches);

        return new ArrayList<>(allMatches);
    }

    public List<Group> searchGroups(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            return Collections.emptyList();
        }

        String searchKey = groupName.toLowerCase();
        return groupNamesMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(searchKey))
                .flatMap(entry -> entry.getValue().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Company> searchPages(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            return Collections.emptyList();
        }

        String searchKey = companyName.toLowerCase();
        return companyNamesMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(searchKey))
                .flatMap(entry -> entry.getValue().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Post> searchPosts(String word) {
        if (word == null || word.isEmpty()) {
            return Collections.emptyList();
        }

        String searchKey = word.toLowerCase();
        return postWordsMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(searchKey))
                .flatMap(entry -> entry.getValue().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
