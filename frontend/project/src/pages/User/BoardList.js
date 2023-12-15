import Layout from '../../layouts/Layout';
import { useRecoilState } from 'recoil';
import { userInfo, adminInfo } from '../../recoil';
import Article from '../../layouts/Article';
import CustomTable from '../../components/Table/CustomTable';
import CustomPagination from '../../components/Pagination/CustomPagination';
import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { mainPageAuthCheck } from '../../AuthCheck';
import { getFetch, postFetch } from '../../config';
import { Button } from '@mui/material';
const headData = ['제목', '작성 날짜', '조회수'];

const BoardList = () => {
  const [user, setUser] = useRecoilState(userInfo);
  const [admin, setAdmin] = useRecoilState(adminInfo);
  const isAdmin = admin.user_id === 'admin' ? true : false;
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
    navigate(`/board/list/${currentPage}`);
  }, [currentPage]);

  useEffect(() => {
    // page가 바뀌면, 실행되는 함수
    const fetchPosts = async (pageNumber) => {
      try {
        const response = await getFetch(`api/board/list/${pageNumber}`);
        if (response.status === 200) {
          const data = await response.json();
          const formattedData = data?.boardList?.map((post) => {
            return { ...post, date: new Date(post.date).toLocaleDateString() };
          });
          setPosts(formattedData.reverse());
          setTotalPages(data.boardCount);
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
    navigate(`/board/view/${row.board_id}`, {
      state: { postNumber: row.board_id }
    });
  };
  return (
    <Layout admin={isAdmin}>
      <Article>
        <h1
          style={{ textAlign: 'left', fontSize: '3rem', marginBottom: '5vh' }}
        >
          자유 게시판
        </h1>
        <div>
          <Button
            variant="contained"
            color="primary"
            sx={{ fontSize: '1rem', float: 'right', margin: '10px' }}
            onClick={() => {
              navigate('/board/create');
            }}
          >
            글 작성
          </Button>
        </div>
        <CustomTable
          headData={headData}
          bodyData={posts}
          onClick={onClickPostHandler}
        />
        <CustomPagination
          page={currentPage}
          totalPages={totalPages}
          setPage={handlePageChange}
        />
      </Article>
    </Layout>
  );
};

export default BoardList;
