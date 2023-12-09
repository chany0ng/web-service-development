import styles from './UserPayCharge.module.scss';
import TabBar from '../../../components/TabBar/TabBar';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Layout from '../../../layouts/Layout';
import Article from '../../../layouts/Article';
import { Card, Button, Container } from '@mui/material';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { userInfo } from '../../../recoil';
import { useRecoilState } from 'recoil';
import { postFetch } from '../../../config';
const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['카드 금액 충전', '추가요금 결제'];
const outerTab = 'payment';
const innerTab = 'charge';
const url = {
  charge: '/user/payment/charge',
  'extra-charge': '/user/payment/extra-charge'
};

const UserPayCharge = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [info, setInfo] = useRecoilState(userInfo);
  const [cash, setCash] = useState({
    cash: ''
  });
  const inputCashHandler = (e) => {
    setCash({ cash: e.target.value });
  };
  const addCashHandler = async (e) => {
    try {
      e.preventDefault();
      const numCash = parseInt(cash.cash, 10);
      if (numCash % 1000 !== 0 || cash.cash.trim() === '')
        throw new Error('정상적인 금액입력이 아닙니다!');
      const response = await postFetch('api/charge', { cash: numCash });
      if (response.status === 200) {
        setInfo((prev) => ({ ...prev, cash: prev.cash + numCash }));
        setCash({ cash: '' });
      } else {
        throw new Error('금액 충전 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <Container maxWidth="sm">
          <Card
            variant="outlined"
            sx={{
              borderColor: 'primary.main',
              padding: '0px',
              borderRadius: '5px'
            }}
            className={styles.card}
          >
            <h2>금액 사전 충전 시스템</h2>
            <div className={styles.favorite}>
              <div className={styles.notice}>
                <h3 style={{ textAlign: 'left' }}>
                  * 충전은 1,000원 단위로 가능합니다
                </h3>
                <h3>
                  * 충전한 금액으로 따릉이 대여, 추가요금 결제가 가능합니다
                </h3>
                <div className={styles.money}>
                  <h3 style={{ marginRight: '10px' }}>현재 보유 금액 : </h3>
                  <h1 className={styles.amount}>
                    {info.cash.toLocaleString()}원
                  </h1>
                </div>
              </div>
              <div className={styles.inputBox}>
                <input
                  type="text"
                  name="cash"
                  id="cash"
                  value={cash.cash}
                  onChange={inputCashHandler}
                  placeholder="충전 할 금액 입력"
                />
                <Button
                  variant="contained"
                  color="primary"
                  sx={{ fontSize: '1rem' }}
                  onClick={addCashHandler}
                >
                  금액 충전
                </Button>
              </div>
            </div>
          </Card>
        </Container>
      </Article>
    </Layout>
  );
};

export default UserPayCharge;
