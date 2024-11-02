package org.hrsh.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchIndex {
    private final Map<String, List<Member>> memberNamesMap;
    private final Map<String, List<Group>> groupNamesMap;
    private final Map<String, List<Page>> pageTitlesMap;
    private final Map<String, List<Post>> postWordsMap;

    public SearchIndex() {
        this.memberNamesMap = new HashMap<>();
        this.groupNamesMap = new HashMap<>();
        this.pageTitlesMap = new HashMap<>();
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

    public void addPageTitle(Page page) {
        var pages = pageTitlesMap.getOrDefault(page.getTitle(), new ArrayList<>());
        pages.add(page);
        pageTitlesMap.put(page.getTitle(), pages);
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

    public List<Page> searchPages(String pageTitle) {
        return pageTitlesMap.getOrDefault(pageTitle, new ArrayList<>());
    }

    public List<Post> searchPosts(String word) {
        return postWordsMap.getOrDefault(word, new ArrayList<>());
    }
}
