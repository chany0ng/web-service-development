import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { postData, getData } from '../../config';
import Layout from '../../layouts/Layout';
import CustomCard from '../../components/Card/CustomCard';
import styles from './UserMainPage.module.scss';
import { useRecoilState } from 'recoil';
import { userInfo } from '../../recoil';
import { mainPageAuthCheck } from '../../AuthCheck';
import { getFetch } from '../../config';

const API_KEY = 'e1228960280fdf5ec15ede4ced0e53aa';
const LAT = 37.619774;
const LON = 127.060926;
const UserMainPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [user, setUser] = useRecoilState(userInfo);
  const getUserInfo = async () => {
    try {
      const response = await getFetch('api/user/main');
      if (response.status === 200) {
        const data = await response.json();
        setUser((prevData) => ({ ...prevData, ...data }));
      } else {
        throw new Error('Get User Data error');
      }
    } catch (error) {
      alert(error);
      console.error(error);
    }
  };
  useEffect(() => {
    getUserInfo();
    getWeather();
  }, []);
  const getWeather = async () => {
    try {
      const url = `https://api.openweathermap.org/data/3.0/onecall?lat=${LAT}&lon=${LON}&exclude={part}&appid=${API_KEY}&lang="kr"`;
      const response = await fetch(url);
      const data = await response.json();
      console.log(data);
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <Layout>
      <CustomCard info={user} />
      <article className={styles.article}></article>
    </Layout>
  );
};

export default UserMainPage;
