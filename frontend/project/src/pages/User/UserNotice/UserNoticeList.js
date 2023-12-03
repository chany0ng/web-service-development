import Layout from '../../../layouts/Layout';
import InnerTabBar from './../../../components/TabBar/InnerTabBar';
import Article from './../../../layouts/Article';
import PostList from '../../../components/List/PostList';
import { useState, useEffect, createContext, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import { postFetch, getFetch } from '../../../config';
import { useNavigate } from 'react-router-dom';
import CustomPagination from '../../../components/Pagination/CustomPagination';

const innerTitle = ['공지사항', '고장 신고'];
const innerTab = 'notice';
const url = {
  notice: '/user/notice/noticeList/1',
  report: '/user/report/reportList'
};
const headData = ['글 번호', '제목', '작성 시각', '조회수'];
const posts = [
  {
    board_id: 1,
    title: '제목1',
    created_at: '2023-12-02T12:34:56Z',
    view: 0
  },
  {
    board_id: 2,
    title: '제목2',
    created_at: '2023-12-02T12:34:56Z',
    view: 0
  }
];
export const MyContext = createContext();

const UserNoticeList = () => {
  const navigate = useNavigate();
  useEffect(() => {
    // 토큰검사
  });
  const { pageNumber } = useParams();
  // const [posts, setPosts] = useState([]);
  const [currentPage, setCurrentPage] = useState(
    pageNumber ? parseInt(pageNumber, 10) : 1
  );
  const [totalPages, setTotalPages] = useState(1);

  // 첫 접속 & 페이지 변경 시 마다 실행
  const handlePageChange = (e, newPage) => {
    setCurrentPage(newPage);
  };
  // page가 바뀌면, 실행되는 함수
  const fetchPosts = async (pageNumber) => {
    try {
      // const { status, data } = await getFetch(`url/${pageNumber}`);
      const status = 200;
      if (status === 200) {
        // setPosts(data);
        setTotalPages(75);
      } else if (status === 401) {
        navigate('/');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  useEffect(() => {
    navigate(`/user/notice/noticeList/${currentPage}`);
  }, [currentPage]);

  useEffect(() => {
    fetchPosts(currentPage);
  }, [currentPage]);

  const onClickPostHandler = (row) => {
    navigate(`/user/notice/noticeView/${row.board_id}`);
    // alert(`row: ${row.board_id}`);
  };

  return (
    <MyContext.Provider value={onClickPostHandler}>
      <Layout>
        <InnerTabBar title={innerTitle} select={innerTab} url={url} />
        <Article>
          <div>공지사항 리스트</div>
          <PostList posts={posts} headData={headData} />
          <CustomPagination
            page={currentPage}
            totalPages={totalPages}
            setPage={handlePageChange}
          />
        </Article>
      </Layout>
    </MyContext.Provider>
  );
};

export default UserNoticeList;
