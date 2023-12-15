import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { getFetch } from '../../config';
import Layout from '../../layouts/Layout';
import PostView from '../../components/PostView/PostView';
import Article from '../../layouts/Article';
import { mainPageAuthCheck } from '../../AuthCheck';
import { adminInfo } from '../../recoil';
import { useRecoilState } from 'recoil';
const BoardView = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [admin, setAdmin] = useRecoilState(adminInfo);
  const isAdmin = admin.user_id === 'admin' ? true : false;
  const location = useLocation();
  const postNumber = location.state?.postNumber;
  const moveToList = () => {
    navigate('/board/list/1');
  };
  const [postContent, setPostContent] = useState({
    user_id: '',
    title: '',
    content: '',
    date: '',
    views: 0,
    author: false
  });
  const fetchPost = async () => {
    try {
      const response = await getFetch(`api/board/info/${postNumber}`);
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
    <Layout admin={isAdmin}>
      <Article>
        <h1
          style={{ textAlign: 'left', fontSize: '3rem', marginBottom: '3vh' }}
        >
          자유 게시판
        </h1>
        <PostView
          data={postContent}
          moveToList={moveToList}
          isBoard={postContent.board_id}
        />
      </Article>
    </Layout>
  );
};
export default BoardView;
