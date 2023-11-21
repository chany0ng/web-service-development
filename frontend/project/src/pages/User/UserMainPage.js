import { useState, useReducer, Fragment } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { postData, getData } from '../../config';
import Layout from '../../layouts/Layout';
import CustomCard from '../../components/Card/CustomCard';
import styles from './UserMainPage.module.scss';
const UserMainPage = () => {
  return (
    <Layout>
      <CustomCard />
      <article>홈페이지에 들어갈 부분</article>
    </Layout>
  );
};

export default UserMainPage;
