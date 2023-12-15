import Article from '../../../layouts/Article';
import Layout from '../../../layouts/Layout';
import CustomPagination from '../../../components/Pagination/CustomPagination';
import CustomTable from '../../../components/Table/CustomTable';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getFetch, postFetch } from '../../../config';
import { Button } from '@mui/material';
import styles from './AdminInfoList.module.scss';
const headData = ['회원 ID', '핸드폰 번호', 'E-mail'];

const AdminInfoList = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [userList, setUserList] = useState([]);
  const [searchId, setSearchId] = useState('');
  const inputIdHandler = (e) => {
    e.preventDefault();
    setSearchId(e.target.value);
  };
  const { pageNumber } = useParams();
  const [currentPage, setCurrentPage] = useState(
    pageNumber ? parseInt(pageNumber, 10) : 1
  );
  const [userCount, setUserCount] = useState(1);
  // 첫 접속 & 페이지 변경 시 마다 실행
  const handlePageChange = (e, newPage) => {
    setCurrentPage(newPage);
  };
  const searchHandler = async (e) => {
    try {
      const response = await postFetch(`api/admin/user/info/list`, {
        user_id: searchId
      });
      if (response.status === 200) {
        const data = await response.json();
        console.log(data);
        setUserCount(data.userCount);
        setUserList(data.userInfoList);
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('사용자 검색 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  useEffect(() => {
    navigate(`/admin/info/list/${currentPage}`);
  }, [currentPage]);
  useEffect(() => {
    // page가 바뀌면, 실행되는 함수
    const fetchUserList = async (pageNumber) => {
      try {
        const response = await getFetch(
          `api/admin/user/info/list/${pageNumber}`
        );
        if (response.status === 200) {
          const data = await response.json();
          console.log(data);
          setUserCount(data.userCount);
          setUserList(data.userInfoList);
        } else if (response.status === 401) {
          navigate('/');
        } else {
          throw new Error('사용자 정보 로딩 에러');
        }
      } catch (error) {
        console.error(error);
        alert(error);
      }
    };
    fetchUserList(currentPage);
  }, [currentPage]);
  return (
    <Layout admin="true">
      <Article>
        <h1
          style={{ textAlign: 'left', fontSize: '3rem', marginBottom: '5vh' }}
        >
          회원정보 조회
        </h1>
        <div className={styles.search}>
          <input
            type="text"
            name="user_id"
            id="user_id"
            value={searchId}
            onChange={inputIdHandler}
            placeholder="회원 ID"
          />
          <Button
            variant="contained"
            color="primary"
            sx={{ fontSize: '1rem' }}
            onClick={searchHandler}
          >
            검색하기
          </Button>
        </div>
        <CustomTable bodyData={userList} headData={headData} />
        <CustomPagination
          page={currentPage}
          totalPages={userCount}
          setPage={handlePageChange}
        />
      </Article>
    </Layout>
  );
};

export default AdminInfoList;
