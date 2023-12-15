import Layout from '../../../layouts/Layout';
import InnerTabBar from './../../../components/TabBar/InnerTabBar';
import Article from './../../../layouts/Article';
import PostList from '../../../components/List/PostList';
import { useState, useEffect, createContext } from 'react';
import { useParams } from 'react-router-dom';
import { getFetch } from '../../../config';
import { useNavigate } from 'react-router-dom';
import CustomPagination from '../../../components/Pagination/CustomPagination';
import { mainPageAuthCheck } from '../../../AuthCheck';
const innerTitle = ['공지사항', '고장 신고'];
const innerTab = 'notice';
const url = {
  notice: '/user/notice/noticeList/1',
  report: '/user/report/reportBoardEdit'
};
const headData = ['제목', '작성 날짜', '조회수'];
export const MyContext = createContext();

const UserNoticeList = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const { pageNumber } = useParams();
  const [posts, setPosts] = useState([]);
  const [currentPage, setCurrentPage] = useState(
    pageNumber ? parseInt(pageNumber, 10) : 1
  );
  const [totalPages, setTotalPages] = useState(1);

  // 첫 접속 & 페이지 변경 시 마다 실행
  const handlePageChange = (e, newPage) => {
    setCurrentPage(newPage);
  };

  useEffect(() => {
    navigate(`/user/notice/noticeList/${currentPage}`);
  }, [currentPage]);

  useEffect(() => {
    // page가 바뀌면, 실행되는 함수
    const fetchPosts = async (pageNumber) => {
      try {
        const response = await getFetch(`api/notice/list/${pageNumber}`);
        if (response.status === 200) {
          const data = await response.json();
          const formattedData = data?.noticeList?.map((post) => {
            return { ...post, date: new Date(post.date).toLocaleDateString() };
          });
          setPosts(formattedData);
          setTotalPages(data.noticeCount);
        } else if (response.status === 401) {
          navigate('/');
        } else {
          throw new Error('게시글 로딩 에러');
        }
      } catch (error) {
        console.error(error);
        alert(error);
      }
    };
    fetchPosts(currentPage);
  }, [currentPage]);

  const onClickPostHandler = (row) => {
    navigate(`/user/notice/noticeView/${row.notice_id}`, {
      state: { postNumber: row.notice_id }
    });
  };

  return (
    <MyContext.Provider value={onClickPostHandler}>
      <Layout>
        <InnerTabBar title={innerTitle} select={innerTab} url={url} />
        <Article>
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
