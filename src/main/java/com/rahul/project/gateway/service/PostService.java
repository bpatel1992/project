package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dao.PostDao;
import com.rahul.project.gateway.dto.PostResponseDTO;
import com.rahul.project.gateway.model.Post;
import com.rahul.project.gateway.model.PostCommentsMapping;
import com.rahul.project.gateway.model.PostLikesMapping;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.utility.CommonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@TransactionalService
public class PostService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    AbstractDao abstractDao;
    @Autowired
    PostDao postDao;
    @Autowired
    CommonUtility commonUtility;

    public Post createPost(Post post) {
        abstractDao.persist(post);
        return post;
    }

    public PostCommentsMapping addComment(PostCommentsMapping comment) {
        abstractDao.persist(comment);
        Post post = abstractDao.getEntityById(Post.class, comment.getPost().getId());
        post.setCommentsCount(post.getCommentsCount() + 1);
        comment.getPost().setCommentsCount(post.getCommentsCount());
        return comment;
    }

    public String likePost(Long postId, Long userId) {
        Post post = abstractDao.getEntityById(Post.class, postId);
        post.setLikesCount(post.getLikesCount() + 1);

        User user = new User();
        user.setId(userId);
        PostLikesMapping postLikesMapping = new PostLikesMapping();
        postLikesMapping.setPost(post);
        postLikesMapping.setUser(user);
        abstractDao.persist(postLikesMapping);
        return "Post Liked Successfully";
    }

    public void updatePost(Post post) {
        abstractDao.saveOrUpdateEntity(post);
    }

    public Post getPostById(Long id) {
        return abstractDao.getEntityById(Post.class, id);
    }

    public List<Post> getPostsList(PostResponseDTO postResponseDTO) throws Exception {
        Long userId = commonUtility.getLoggedInUser();
        logger.info("User Id : " + userId);

        List<Post> posts = postDao.getPostsByUserId(userId);
        return posts;
    }

    public List<PostCommentsMapping> getPostComments(Long postId) throws Exception {
        logger.info("Post Id : " + postId);

        return postDao.getPostComments(postId);
    }
}
