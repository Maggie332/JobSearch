package com.laioffer.job.recommendation;

import com.laioffer.job.db.MySQLConnection;
import com.laioffer.job.entity.Item;
import com.laioffer.job.external.GitHubClient;

import java.util.*;

public class Recommendation {
    public List<Item>  recommendItems(String userId, double lat, double lon) {

        //step1 get all favorited itemIds
        List<Item> recommendedItems = new ArrayList<>();
        MySQLConnection connection = new MySQLConnection();
        Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);

       //step2 get all the keywords, sort by count
        Map<String, Integer> allKeywords = new HashMap<>();
        for (String itemId : favoritedItemIds) {
            Set<String> keywords = connection.getKeywords(itemId);
            for (String keyword : keywords) {
                allKeywords.put(keyword, allKeywords.getOrDefault(keyword,0) +1);
            }
        }

        connection.close();

        //keywords sorting
        List<Map.Entry<String, Integer>> keywordList = new ArrayList<>(allKeywords.entrySet());
        keywordList.sort((Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) ->
                Integer.compare(e2.getValue(), e1.getValue()));

        //cut down search list only by top 3
        if (keywordList.size() > 3) {
            keywordList = keywordList.subList(0, 3);
        }

        //step3 search based on keywords, filter out those already in history
        Set<String> visitedItemIds = new HashSet<>();
        GitHubClient client = new GitHubClient();

        for (Map.Entry<String, Integer> keyword : keywordList) {
            List<Item> items = client.search(lat, lon, keyword.getKey());

            for (Item item : items) {
                if (!favoritedItemIds.contains(item.getId()) && !visitedItemIds.contains(item.getId())) {
                    recommendedItems.add(item);
                    visitedItemIds.add(item.getId());
                }
            }
        }
        return recommendedItems;
    }
}

