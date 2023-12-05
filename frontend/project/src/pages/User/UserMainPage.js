import { useState, useReducer, Fragment } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { postData, getData } from '../../config';
import Layout from '../../layouts/Layout';
import CustomCard from '../../components/Card/CustomCard';
import styles from './UserMainPage.module.scss';
import { useRecoilState } from 'recoil';
import { userInfo } from '../../recoil';
const UserMainPage = () => {
  const [user, setUser] = useRecoilState(userInfo);
  return (
    <Layout>
      <CustomCard />
      <article className={styles.article}></article>
    </Layout>
  );
};

export default UserMainPage;
