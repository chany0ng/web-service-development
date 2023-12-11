import Layout from '../../../layouts/Layout';
import TabBar from '../../../components/TabBar/TabBar';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Article from '../../../layouts/Article';
import styles from './UserInfoBookMark.module.scss';
import { Card, Container, Button } from '@mui/material';
import { useState, useEffect } from 'react';
import CustomTable from '../../../components/Table/CustomTable';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { useNavigate } from 'react-router-dom';
import { getFetch, postFetch } from '../../../config';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['개인정보 수정', '대여소 즐겨찾기'];
const outerTab = 'info';
const innerTab = 'bookmark';
const url = { edit: '/user/info/edit', bookmark: '/user/info/bookmark' }; // 테이블에 넘길 값
const head = ['대여소 번호', '대여소 주소', '즐겨찾기 해제'];

const UserInfoBookMark = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [station, setStation] = useState('');
  const [searchedStation, setSearchedStation] = useState([]);
  const [favorites, setFavorites] = useState([]);
  const inputStationHandler = (e) => {
    e.preventDefault();
    const newStation = e.target.value;
    setStation(newStation);
  };
  const addFavoriteHandler = async (address) => {
    try {
      const response = await postFetch('api/favorites/change', {
        location: address,
        favorite: true
      });
      if (response.status === 200) {
        setFavorites((prev) => [
          ...prev,
          {
            location: address,
            favorite: (
              <DeleteForeverIcon
                onClick={() => deleteFavoriteHandler(address)}
                sx={{ fontSize: '3rem' }}
              />
            )
          }
        ]);
        alert('즐겨찾기가 추가되었습니다!');
        window.location.reload();
      } else if (response.status === 409) {
        alert('즐겨찾기가 중복됩니다!');
      } else {
        throw new Error('즐겨찾기 변경 에러');
      }
    } catch (error) {
      alert(error);
      console.error(error);
    }
  };
  const deleteFavoriteHandler = async (address) => {
    try {
      const response = await postFetch('api/favorites/change', {
        location: address,
        favorite: false
      });
      if (response.status === 200) {
        setFavorites((prevFavorites) => {
          const updatedFavorites = prevFavorites.filter(
            (item) => item.address !== address
          );
          return updatedFavorites;
        });
      } else if (response.status === 409) {
        alert('즐겨찾기가 중복됩니다!');
      } else {
        throw new Error('즐겨찾기 변경 에러');
      }
    } catch (error) {
      alert(error);
      console.error(error);
    }
  };
  const addStationHandler = async (e) => {
    try {
      e.preventDefault();
      const response = await postFetch('api/favorites/list', {
        location: station
      });
      if (response.status === 200) {
        const data = await response.json();
        setSearchedStation(data.locations);
      } else {
        throw new Error('대여소 검색 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  useEffect(() => {
    const getFavoritesList = async () => {
      try {
        const response = await getFetch('api/favorites/list');
        if (response.status === 200) {
          const data = await response.json();
          const formattedData = data.locations.map((location) => {
            return {
              ...location,
              favorite: (
                <DeleteForeverIcon
                  onClick={() => deleteFavoriteHandler(location.address)}
                  sx={{ fontSize: '3rem' }}
                />
              )
            };
          });
          setFavorites(formattedData);
        } else {
          throw new Error('즐겨찾기 대여소 리스트 조회 오류');
        }
      } catch (error) {
        console.error(error);
        alert(error);
      }
    };
    getFavoritesList();
  }, []);
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <Container maxWidth="xs">
          <Card
            variant="outlined"
            sx={{
              borderColor: 'primary.main',
              padding: '0px',
              borderRadius: '5px'
            }}
            className={styles.card}
          >
            <h2>대여소 즐겨찾기 추가</h2>
            <div className={styles.favorite}>
              <input
                type="text"
                name="location_id"
                id="location_id"
                value={station}
                onChange={inputStationHandler}
                placeholder="대여소 이름"
              />
              <Button
                variant="contained"
                color="primary"
                sx={{ fontSize: '1rem' }}
                onClick={addStationHandler}
              >
                검색하기
              </Button>
            </div>
            {station && (
              <div className={styles.search}>
                {searchedStation.map((station, index) => (
                  <div
                    key={index}
                    className={styles.each}
                    onClick={() => addFavoriteHandler(station.address)}
                  >
                    {station.address}
                  </div>
                ))}
              </div>
            )}
          </Card>
        </Container>
        <h2
          style={{ textAlign: 'center', marginTop: '70px', fontSize: '2rem' }}
        >
          즐겨찾는 대여소 목록
        </h2>
        <CustomTable headData={head} bodyData={favorites} />
      </Article>
    </Layout>
  );
};

export default UserInfoBookMark;
