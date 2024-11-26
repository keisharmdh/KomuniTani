package com.example.api;

import com.example.model.ApiResponse;
import com.example.model.ChatMessage;
import com.example.model.ChatResponse;
import com.example.model.DeleteResponse;
import com.example.model.FollowResponse;
import com.example.model.LikeResponse;
import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import com.example.model.MessageResponse;
import com.example.model.MsgModel;
import com.example.model.NewChatMessage;
import com.example.model.NewMessageResponse;
import com.example.model.PasswordChangeResponse;
import com.example.model.Post;
import com.example.model.PostRequest;
import com.example.model.PostResponse;
import com.example.model.ProfileResponse;
import com.example.model.ProfileUpdateResponse;
import com.example.model.RegisterRequest;
import com.example.model.RegisterResponse;
import com.example.model.SearchResponse;
import com.example.model.User;
import com.example.model.UserResponse;
import com.example.model.chattingmessage;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.HashMap;
import java.util.Map;

public interface ApiService {
    // Ambil post dengan endpoint yang tepat

    @Headers("Accept: application/json")
    @GET("v/posts")
    Call<ApiResponse<Post>> getPosts();

    @Headers("Accept: application/json")
    @POST("v/posts")  // Sesuaikan dengan endpoint yang benar
    Call<PostResponse> createPost(@Body PostRequest postRequest);


    // Endpoint untuk registrasi pengguna
    @POST("v/register") // Sesuaikan dengan endpoint yang sesuai
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("v/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest); // Tambahkan anotasi @Body


    @GET("d/users/{id}")
    Call<User> getUserData(@Path("id") int userId, @Header("Authorization") String token);


    @GET("d/users") // Ubah sesuai endpoint API Anda
    Call<ApiResponse<User>> getUsers();

    @GET("d/users/{id}")
    Call<User> getName(@Path("id") int id);

    @Headers("Accept: application/json")
    @GET("v/profile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @PUT("v/profile")
    Call<ProfileUpdateResponse> updateProfile(
            @Header("Authorization") String token,
            @Body Map<String, String> profileData
    );

    @Headers("Accept: application/json")
    @PUT("v/password")
    Call<PasswordChangeResponse> updatePassword(@Header("Authorization") String token, @Body Map<String, String> passwordData);




//    @POST("v/profile")
//    Call<ProfileResponse> updateProfile(@Header("Authorization") String token, @Body ProfileRequest profileRequest);

    @Headers("Accept: application/json")
    @GET("v/profile/{id}")
    Call<UserResponse> getUserProfile(@Path("id") String userId);

    @Headers("Accept: application/json")
    @GET("v/profile/{id}")
    Call<ApiResponse<Post>> getLikedPosts(@Path("id") String userId);


    @Headers("Accept: application/json")
    @POST("v/users/{id}/follow")
    Call<FollowResponse> followUser(@Path("id") String userId);

    @Headers("Accept: application/json")
    @POST("v/users/{id}/unfollow")
    Call<FollowResponse> unfollowUser(@Path("id") String userId);

//    @GET("v/users/{id}")
//    Call<UserResponse> getProfile(
////            @Header("Authorization") String token,
////            @Path("id") int userId
//    );

    @Headers("Accept: application/json")
    @GET("v/search")
    Call<SearchResponse> searchPosts(
            @Query("query") String query
    );

    @GET("get")
    Call<MsgModel> getMessage(
            @Query("bid") String bid,
            @Query("key") String key,
            @Query("uid") String uid,
            @Query("msg") String msg
    );


//    @Headers("Accept: application/json")
//    @POST("v/posts/{postId}/comment")
//    Call<CommentResponse> postComment(
//            @Path("postId") int postId,
//            @Body CommentRequest request,
//            @Header("Authorization") String token
//    );

    @POST("v/posts/{postId}/like")
    Call<LikeResponse> likePost(@Path("postId") int postId, @Body HashMap<String, Integer> requestBody);



//    @GET("v/posts/{id}/comments")
//    Call<List<Comment>> getComments(@Path("id") int postId);

    @Headers("Accept: application/json")
    @PUT("v/posts/{postId}")
    Call<Post> updatePost(@Path("postId") int postId, @Body HashMap<String, Object> requestBody);

    @DELETE("v/posts/{postId}")
    Call<DeleteResponse> deletePost(@Path("postId") int postId);

    @Headers("Accept: application/json")
    @GET("v/messages")
    Call<MessageResponse> getMessages(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @GET("v/messages/{receiverId}")
    Call<ChatResponse> getMessages(
            @Path("receiverId") int receiverId,
            @Header("Authorization") String token // Menambahkan header Authorization
    );

//    @POST("v/messages/{receiverId}")
//    @Headers("Accept: application/json")
//    Call<ResponseBody> sendMessage(
//            @Path("receiverId") int receiverId,
//            @Header("Authorization") String token,
//            @Body ChatMessage message
//    );

    @POST("v/messages/{receiverId}")
    Call<NewMessageResponse> sendNewMessage(
            @Path("receiverId") int receiverId,
            @Header("Authorization") String token,
            @Body NewChatMessage message
    );




}














