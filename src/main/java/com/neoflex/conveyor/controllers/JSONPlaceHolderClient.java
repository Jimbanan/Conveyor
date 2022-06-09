//package com.neoflex.conveyor.controllers;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com/")
//public interface JSONPlaceHolderClient {
//
//    @RequestMapping(method = RequestMethod.GET, value = "/posts")
//    List<Post> getPosts();
//
//    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
//    Post getPostById(@PathVariable("postId") Long postId);
//}