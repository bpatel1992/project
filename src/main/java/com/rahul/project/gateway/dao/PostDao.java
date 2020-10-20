package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.configuration.annotations.RepositoryDao;
import com.rahul.project.gateway.model.Post;
import com.rahul.project.gateway.model.PostCommentsMapping;
import com.rahul.project.gateway.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author rahul malhotra
 * @Date 2019-05-24
 */

@RepositoryDao
public class PostDao {

    @Autowired
    AbstractDao abstractDao;

    public List<Post> getPostsByUserId(long userId) throws Exception {
        CriteriaBuilder builder = abstractDao.getSession().getCriteriaBuilder();
        CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
        Root<Post> root = criteria.from(Post.class);
        Join<Post, User> userJoin = root.join("postByUser");
        criteria.where(builder.equal(userJoin.get("id"), userId));
        TypedQuery<Post> deviceQuery = abstractDao.getSession().createQuery(criteria);
        return deviceQuery.getResultList();

    }

    public List<PostCommentsMapping> getPostComments(Long postId) throws Exception {
        CriteriaBuilder builder = abstractDao.getSession().getCriteriaBuilder();
        CriteriaQuery<PostCommentsMapping> criteria = builder.createQuery(PostCommentsMapping.class);
        Root<PostCommentsMapping> root = criteria.from(PostCommentsMapping.class);
        Join<PostCommentsMapping, Post> postJoin = root.join("post");
        criteria.where(builder.equal(postJoin.get("id"), postId));
        TypedQuery<PostCommentsMapping> deviceQuery = abstractDao.getSession().createQuery(criteria);
        return deviceQuery.getResultList();
    }

}
