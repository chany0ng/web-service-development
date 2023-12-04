import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { postFetch } from '../../../config';

const UserNoticeView = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const postNumber = location.state?.postNumber;
  const [postContent, setPostContent] = useState('');
  const fetchPostDetail = async () => {
    try {
      const { status, data } = await postFetch('url', { board_id: postNumber });
      alert('상세 페이지: ', status, data);
      if (status === 200) {
        setPostContent(data);
      }
      if (status === 401) {
        navigate('/');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  useEffect(() => {
    const fetchData = async () => {
      await fetchPostDetail();
    };
    fetchData();
  }, [postNumber]);
  return <div>여기는 게시글 상세 뷰</div>;
};
export default UserNoticeView;
