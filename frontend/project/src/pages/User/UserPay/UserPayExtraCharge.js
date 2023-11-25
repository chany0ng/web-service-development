import TabBar from '../../../components/TabBar/TabBar';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Layout from '../../../layouts/Layout';
import Article from '../../../layouts/Article';
import styles from './UserPayExtraCharge.module.scss';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';

const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['카드 금액 충전', '추가요금 결제'];
const outerTab = 'pay';
const innerTab = 'extra-charge';
const url = {
  charge: '/user/pay/charge',
  'extra-charge': '/user/pay/extra-charge'
};

const UserPayExtraCharge = () => {
  const existMoney = 1500;
  const overFee = 2000;
  const [isLackOfMoney, setIsLackOfMoney] = useState(false);
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  useEffect(() => {
    console.log('effect!');
    if (overFee > existMoney) {
      setIsLackOfMoney(true);
      setIsButtonEnabled(true);
    } else if (overFee === 0) {
      setIsLackOfMoney(false);
      setIsButtonEnabled(true);
    } else {
      setIsLackOfMoney(false);
      setIsButtonEnabled(false);
    }
  }, [existMoney, overFee]);

  const navigate = useNavigate();
  const movePageHandler = () => {
    navigate('/user/pay/charge');
  };
  const onSubmitHandler = (e) => {
    e.preventDefault();
    //todo 보유금액에서 미납금액만큼 차감 후, post요청
  };
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <section className={styles.flexContainer}>
          <div className={styles.flexItem}>
            <span className={styles.first}>보유 금액 : </span>
            <span className={styles.second}>
              {existMoney.toLocaleString()}원
            </span>
          </div>
          <div className={styles.flexItem}>
            <span className={styles.first}>미납 금액 : </span>
            <span className={styles.second}>{overFee.toLocaleString()}원</span>
          </div>
          {isLackOfMoney && (
            <div>
              <h2 className={styles.moneyLack}>보유 금액 부족!</h2>
              <Button
                variant="contained"
                color="primary"
                sx={{ fontSize: '1rem' }}
                onClick={movePageHandler}
              >
                충전 페이지로 이동
              </Button>
            </div>
          )}
          <Button
            onClick={onSubmitHandler}
            type="submit"
            variant="contained"
            disabled={isButtonEnabled}
            sx={{
              mt: 3,
              width: '20vw',
              fontSize: '1.1rem',
              backgroundColor: 'secondary.main',
              '&:hover': {
                backgroundColor: 'secondary.main'
              }
            }}
          >
            결제하기
          </Button>
        </section>
      </Article>
    </Layout>
  );
};

export default UserPayExtraCharge;
