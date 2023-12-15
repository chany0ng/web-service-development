import Article from '../../../layouts/Article';
import Layout from '../../../layouts/Layout';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import PostList from '../../../components/List/PostList';
import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getFetch, postFetch } from '../../../config';
import CustomPagination from '../../../components/Pagination/CustomPagination';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { Button } from '@mui/material';

const innerTitle = ['공지사항', '고장 신고'];
const innerTab = 'report';
const url = {
  notice: '/admin/notice/list/1',
  report: '/admin/report/list/1'
};
const headData = ['자전거', '신고자 ID', '작성 시각', '고장 부위', '수리하기'];

const AdminReportList = () => {
  const navigate = useNavigate();
  const selectedKeys = ['bike_id', 'user_id', 'created_at'];
  const keyMapping = {
    chain: '체인',
    saddle: '안장',
    pedal: '페달',
    terminal: '단말기',
    tire: '타이어'
  };
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  // const [repairList, setRepairList] = useState([])
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
    navigate(`/admin/report/list/${currentPage}`);
  }, [currentPage]);

  useEffect(() => {
    // page가 바뀌면, 실행되는 함수
    const fetchPosts = async (pageNumber) => {
      try {
        const response = await getFetch(`api/admin/report/list/${pageNumber}`);
        if (response.status === 200) {
          const data = await response.json();
          const repairList = setRepairList(data.reportList);
          const trueReportList = data.reportList.map((item) => {
            const newObj = {};
            selectedKeys.forEach((key) => {
              newObj[key] = item[key];
            });
            newObj.repair = repairList;
            newObj.button = (
              <Button
                variant="contained"
                color="primary"
                sx={{ fontSize: '1rem' }}
                onClick={() => {
                  repairHandler(item.bike_id);
                }}
              >
                수리하기
              </Button>
            );
            return newObj;
          });
          setPosts(trueReportList);
          setTotalPages(data.reportCount);
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
  const setRepairList = (list) => {
    const keyList = list
      .map((bike) => {
        const convertedBike = {};
        Object.keys(bike).forEach((key) => {
          const koreanKey = keyMapping[key] || key;
          convertedBike[koreanKey] = bike[key];
        });
        const array = [];
        for (const [key, value] of Object.entries(convertedBike)) {
          if (value === true) {
            array.push(key);
          }
        }
        return array;
      })
      .join(',');
    return keyList;
  };
  const repairHandler = async (bike_id) => {
    try {
      const response = await postFetch('api/admin/report/repair', {
        bike_id: bike_id
      });
      if (response.status === 200) {
        alert('따릉이가 수리되었습니다');
        window.location.reload();
      } else {
        throw new Error('따릉이 수리 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  return (
    <Layout admin="true">
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
  );
};

export default AdminReportList;
