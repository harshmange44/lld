package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatchService {
    private Map<String, Match> matches;

    public MatchService() {
        this.matches = new ConcurrentHashMap<>();
    }

    public void addMatch(Match match) {
        matches.put(match.getId(), match);
    }

    public Match getMatch(String matchId) {
        return matches.get(matchId);
    }

    public List<Match> getAllMatches() {
        return new ArrayList<>(matches.values());
    }

    public void updateMatchStatus(String matchId, MatchStatus matchStatus) {
        Match match = matches.get(matchId);
        if (match != null) {
            match.setMatchStatus(matchStatus);
        }
    }
}
