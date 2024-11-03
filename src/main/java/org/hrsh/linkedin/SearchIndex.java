package org.hrsh.linkedin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchIndex {
    private final Map<String, List<Member>> memberNamesMap;
    private final Map<String, List<Group>> groupNamesMap;
    private final Map<String, List<Company>> companyNamesMap;
    private final Map<String, List<Post>> postWordsMap;

    public SearchIndex() {
        this.memberNamesMap = new HashMap<>();
        this.groupNamesMap = new HashMap<>();
        this.companyNamesMap = new HashMap<>();
        this.postWordsMap = new HashMap<>();
    }

    public void addMemberName(Member member) {
        var members = memberNamesMap.getOrDefault(member.getName(), new ArrayList<>());
        members.add(member);
        memberNamesMap.put(member.getName(), members);
    }

    public void addGroupName(Group group) {
        var groups = groupNamesMap.getOrDefault(group.getName(), new ArrayList<>());
        groups.add(group);
        groupNamesMap.put(group.getName(), groups);
    }

    public void addCompanyName(Company company) {
        var companies = companyNamesMap.getOrDefault(company.getName(), new ArrayList<>());
        companies.add(company);
        companyNamesMap.put(company.getName(), companies);
    }

    public void addPostWords(Post post) {
        String[] words = post.getText().split(" ");
        for (String word : words) {
            var posts = postWordsMap.getOrDefault(word, new ArrayList<>());
            posts.add(post);
            postWordsMap.put(word, posts);
        }
    }

    public List<Member> searchMembers(String memberName) {
        return memberNamesMap.getOrDefault(memberName, new ArrayList<>());
    }

    public List<Group> searchGroups(String groupName) {
        return groupNamesMap.getOrDefault(groupName, new ArrayList<>());
    }

    public List<Company> searchPages(String companyName) {
        return companyNamesMap.getOrDefault(companyName, new ArrayList<>());
    }

    public List<Post> searchPosts(String word) {
        return postWordsMap.getOrDefault(word, new ArrayList<>());
    }
}
