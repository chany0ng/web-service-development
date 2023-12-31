import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { postFetch, getFetch } from '../../../config';
import Layout from '../../../layouts/Layout';
import PostView from '../../../components/PostView/PostView';
import Article from '../../../layouts/Article';
import { mainPageAuthCheck } from '../../../AuthCheck';

const UserNoticeView = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const location = useLocation();
  const postNumber = location.state?.postNumber;
  const moveToList = () => {
    navigate('/user/notice/noticeList/1');
  };
  const [postContent, setPostContent] = useState({
    title: '',
    content: '',
    date: '',
    views: 0
  });
  const fetchPost = async () => {
    try {
      const response = await getFetch(`api/notice/info/${postNumber}`);
      const data = await response.json();
      if (response.status === 200) {
        const realDate = new Date(data.date).toLocaleDateString();
        setPostContent((prevData) => ({
          ...prevData,
          ...data,
          date: realDate
        }));
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error(`게시물 정보 로드 실패: ${response.status}`);
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  useEffect(() => {
    fetchPost();
  }, []);
  return (
    <Layout>
      <Article>
        <h1
          style={{ textAlign: 'left', fontSize: '3rem', marginBottom: '3vh' }}
        >
          공지사항
        </h1>
        <PostView data={postContent} moveToList={moveToList} />
      </Article>
    </Layout>
  );
};
export default UserNoticeView;
