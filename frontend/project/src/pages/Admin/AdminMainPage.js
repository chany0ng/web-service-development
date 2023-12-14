import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { mainPageAuthCheck } from '../../AuthCheck';
import { adminInfo } from '../../recoil';
import { getFetch } from '../../config';
import Layout from '../../layouts/Layout';
import { useRecoilState } from 'recoil';
import CustomCard from '../../components/Card/CustomCard';
import styles from './AdminMainPage.module.scss';
const AdminMainPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [admin, setAdmin] = useRecoilState(adminInfo);
  const getAdminInfo = async () => {
    try {
      const response = await getFetch('api/admin/main');
      if (response.status === 200) {
        const data = await response.json();
        setAdmin((prevData) => ({ ...prevData, ...data }));
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('Get Admin Data error');
      }
    } catch (error) {
      alert(error);
      console.error(error);
    }
  };
  useEffect(() => {
    getAdminInfo();
  }, []);
  return (
    <Layout admin="true">
      <CustomCard info={admin} />
      <article className={styles.article}></article>
    </Layout>
  );
};

export default AdminMainPage;
