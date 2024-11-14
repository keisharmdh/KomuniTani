package com.example.komunitani;


import com.example.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PopularData {

    public static List<Post> getPopularPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Post A", "Content A", 150, 20, 50));
        posts.add(new Post("Post B", "Content B", 200, 25, 100));
        posts.add(new Post("Post C", "Content C", 120, 30, 80));

        // Sorting berdasarkan total popularitas
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                int p1Score = p1.getLikeCount() + p1.getCommentCount() + p1.getShareCount();
                int p2Score = p2.getLikeCount() + p2.getCommentCount() + p2.getShareCount();
                return Integer.compare(p2Score, p1Score); // Descending order
            }
        });

        return posts;
    }
}
