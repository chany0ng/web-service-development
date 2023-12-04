import CustomTable from '../Table/CustomTable';
const PostList = ({ posts, headData }) => {
  const formattedPostData = posts?.map((post) => {
    return {
      ...post,
      created_at: new Date(post.created_at).toLocaleString()
    };
  });
  return <CustomTable headData={headData} bodyData={formattedPostData} />;
};

export default PostList;
