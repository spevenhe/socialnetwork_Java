package com.myapp.socialnetwork.socialnetwork.DAO;

import com.myapp.socialnetwork.socialnetwork.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param 用于取别名，如果方法只有一个参数，并且在if里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);
}
