package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.UserDetailsServiceImpl;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.model.Post;
import com.rahul.project.gateway.model.PostCommentsMapping;
import com.rahul.project.gateway.service.PostService;
import com.rahul.project.gateway.utility.CommonUtility;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Controller
@RequestMapping("/oauth2/api/post/")
class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonUtility commonUtility;

    @ApiOperation(value = "Fetch user post details by user", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/get-user-posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PostResponseDTO getUserPosts(@RequestBody PostResponseDTO postResponseDTO) throws Exception {
        //...
        List<Post> posts = postService.getPostsList(postResponseDTO);
        if (posts != null) {
            postResponseDTO.setPosts(posts.stream()
                    .map(post -> convertToDto(post))
                    .collect(Collectors.toList()));
        }

        return postResponseDTO;
    }

    @ApiOperation(value = "Fetch post comment details by post Id", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/get-post-comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PostCommentResponseDTO getPostComments(@RequestBody PostCommentResponseDTO postCommentResponseDTO) throws Exception {
        List<PostCommentsMapping> postComments = postService.getPostComments(postCommentResponseDTO.getPostId());
        if (postComments != null) {
            postCommentResponseDTO.setComments(postComments.stream()
                    .map(comment -> convertCommentEntity(comment))
                    .collect(Collectors.toList()));
        }
        return postCommentResponseDTO;
    }

    @ApiOperation(value = "Create post by user", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PostDto createPost(@RequestBody PostDto postDto) throws Exception {
        Post post = convertToEntity(postDto);
        Post postCreated = postService.createPost(post);
        return convertToDto(postCreated);
    }

    @ApiOperation(value = "Add comment by user", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PostCommentDTO addPostComment(@RequestBody PostCommentDTO commentDTO) throws Exception {
        PostCommentsMapping comment = convertCommentDto(commentDTO);
        comment = postService.addComment(comment);
        return convertCommentEntity(comment);
    }

    @ApiOperation(value = "Like post by user", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/likePost", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String likePost(@RequestBody PostLikeDTO likeDTO) throws Exception {
        String result = postService.likePost(likeDTO.getPostId(), likeDTO.getUserId());
        return result;
    }

    @ApiOperation(value = "Fetch post by id", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PostDto getPost(@PathVariable("id") Long id) {
        return convertToDto(postService.getPostById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@RequestBody PostDto postDto) throws Exception {
        Post post = convertToEntity(postDto);
        postService.updatePost(post);
    }


    private PostDto convertToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
//        postDto.setSubmissionDateConverted(post.getSubmissionDate(),
//                userService.getUserById(post.getPostByUser().getId()).getTimezone());
//        postDto.setLastModifiedDateConverted(post.getLastModifiedDate(),
//                userService.getUserById(post.getPostByUser().getId()).getTimezone());
        return postDto;
    }

    private Post convertToEntity(PostDto postDto) throws ParseException {
        Post post = modelMapper.map(postDto, Post.class);
//        post.setSubmissionDate(postDto.getSubmissionDateConverted(
//                userService.getUserById(postDto.getPostByUser().getId()).getTimezone()));
//        post.setLastModifiedDate(postDto.getLastModifiedDateConverted(
//                userService.getUserById(postDto.getPostByUser().getId()).getTimezone()));

        if (postDto.getId() != null) {
            Post oldPost = postService.getPostById(postDto.getId());
            //TODO
        }
        return post;
    }

    private PostCommentsMapping convertCommentDto(PostCommentDTO commentDTO) throws ParseException {
        PostCommentsMapping postComment = modelMapper.map(commentDTO, PostCommentsMapping.class);
//        if (commentDTO.getCreationDate() != null) {
//            postComment.setCreationDate(commonUtility.getDateConverted(commentDTO.getCreationDate()));
//        }
//
//        if (commentDTO.getModificationDate() != null) {
//            postComment.setModificationDate(commonUtility.getDateConverted(commentDTO.getModificationDate()));
//        }
        return postComment;
    }

    private PostCommentDTO convertCommentEntity(PostCommentsMapping comment) {
        PostCommentDTO commentDTO = modelMapper.map(comment, PostCommentDTO.class);

        return commentDTO;
    }
}
