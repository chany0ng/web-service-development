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
  }, []);
  return (
    <Layout>
      <CustomCard info={user} />
      <article className={styles.article}></article>
    </Layout>
  );
};

export default UserMainPage;
