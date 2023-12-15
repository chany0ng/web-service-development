import TabBar from '../../../components/TabBar/TabBar';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Layout from '../../../layouts/Layout';
import Article from '../../../layouts/Article';
import styles from './UserPayExtraCharge.module.scss';
import { Button, typographyClasses } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { mainPageAuthCheck } from '../../../AuthCheck';
import { userInfo } from '../../../recoil';
import { useRecoilState } from 'recoil';
import { getFetch } from '../../../config';
const outerTitle = ['회원정보 관리', '결제 관리', '이용정보 관리'];
const innerTitle = ['카드 금액 충전', '추가요금 결제'];
const outerTab = 'payment';
const innerTab = 'extra-charge';
const url = {
  charge: '/user/payment/charge',
  'extra-charge': '/user/payment/extra-charge'
};

const UserPayExtraCharge = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  useEffect(() => {
    const getOverFee = async () => {
      try {
        const response = await getFetch('api/surcharge/info');
        if (response.status === 200) {
          const data = await response.json();
          setOverFee(data.overfee);
        } else {
          throw new Error('추가요금 조회 에러');
        }
      } catch (error) {
        alert(error);
        console.error(error);
      }
    };
    getOverFee();
  }, []);
  const [info, setInfo] = useRecoilState(userInfo);
  const [overFee, setOverFee] = useState(0);
  const lackNotice = (
    <h2 className={styles.moneyLack}>보유 금액이 부족합니다!</h2>
  );
  const [isLackOfMoney, setIsLackOfMoney] = useState(false);
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  useEffect(() => {
    if (overFee > info.cash) {
      setIsLackOfMoney(true);
      setIsButtonEnabled(true);
    } else if (overFee === 0) {
      setIsLackOfMoney(false);
      setIsButtonEnabled(true);
    } else {
      setIsLackOfMoney(false);
      setIsButtonEnabled(false);
    }
  }, [info.cash, overFee]);

  const movePageHandler = () => {
    navigate('/user/payment/charge');
  };
  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();

      const response = await getFetch('api/surcharge/pay');
      if (response.status === 200) {
        setInfo((prev) => ({ ...prev, cash: prev.cash - overFee }));
        setOverFee(0);
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('추가요금 결제 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  const noLackNotice = (
    <h2>
      결제 후 남은 금액:
      <span style={{ fontSize: '1.8rem' }}>
        {(info.cash - overFee).toLocaleString()}원
      </span>
    </h2>
  );
  return (
    <Layout>
      <TabBar title={outerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <div className={styles.notice}>
          <p>
            <span style={{ color: 'red' }}>이용 가능시간</span>은 첫 회
            대여시점을 기준으로 계산합니다.
          </p>
          <p>서울자전거 모든 대여소에서 사용이 가능합니다.</p>
          <p>
            서울자전거 <span style={{ color: 'red' }}>환불규정</span>에
            따릅니다.
          </p>
          <p>
            환불 규정 보기 이용권을 다른 사람에게 양도할 수 없으며,양도로 인해
            발생하는 불이익은 구매자가 책임지셔야 합니다.
          </p>
        </div>
        <section className={styles.flexContainer}>
          <div className={styles.flexItem}>
            <span className={styles.first}>보유 금액 : </span>
            <span className={styles.second}>
              {info.cash.toLocaleString()}원
            </span>
          </div>
          <div className={styles.flexItem}>
            <span className={styles.first}>미납 금액 : </span>
            <span className={styles.second}>{overFee.toLocaleString()}원</span>
          </div>
          <div className={styles.payBox}>
            {isLackOfMoney ? lackNotice : noLackNotice}
            {isLackOfMoney && (
              <Button
                variant="contained"
                color="primary"
                sx={{ fontSize: '1rem' }}
                onClick={movePageHandler}
              >
                충전 페이지로 이동
              </Button>
            )}
          </div>
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
