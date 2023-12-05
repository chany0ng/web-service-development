import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import Layout from '../../../layouts/Layout';
import Article from '../../../layouts/Article';
import styles from './UserTicketBuy.module.scss';
import { Card, Button, Container } from '@mui/material';
import Checkbox from '@mui/material/Checkbox';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { userInfo } from '../../../recoil';
import { mainPageAuthCheck } from '../../../AuthCheck';
const innerTitle = ['일일권 구매', '일일권 선물'];
const innerTab = 'buy';
const url = {
  buy: '/user/tickets/purchase',
  gift: '/user/tickets/gift'
};
const UserTicketBuy = () => {
  const navigate = useNavigate();
  useEffect(() => {
    mainPageAuthCheck(navigate);
  }, []);
  const [user, setUser] = useRecoilState(userInfo);
  const [checked, setChecked] = useState(false);
  const [isLackOfMoney, setIsLackOfMoney] = useState(false);
  const existMoney = 0;
  const ticketPrice = 1000;
  const afterMoney = existMoney - ticketPrice;
  const lackNotice = (
    <h1 className={styles.moneyLack}>보유 금액이 부족합니다!</h1>
  );
  const movePageHandler = () => {
    navigate('/user/pay/charge');
  };
  const handleChange = (event) => {
    setChecked(event.target.checked);
  };

  useEffect(() => {
    if (ticketPrice > existMoney) {
      setIsLackOfMoney(true);
    } else {
      setIsLackOfMoney(false);
    }
  }, [existMoney, ticketPrice]);

  const onSubmitHandler = (e) => {
    e.preventDefault();
    //todo 보유금액에서 미납금액만큼 차감 후, post요청
  };

  return (
    <Layout>
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <div className={styles.notice}>
          <p>
            <span style={{ color: 'red' }}>1회 1매</span>씩 구매 가능합니다.
          </p>
          <p>대여시간은 기본 1시간입니다.</p>
          <p>
            초과 시 15분마다 추가요금{' '}
            <span style={{ color: 'red' }}>250원</span>이 부과됩니다.
          </p>
          <p>예시 - 기본 초과 1~15분: 250원, 16~30분: 500원</p>
        </div>
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
            <h2>이용권 구매하기</h2>
            <div className={styles.favorite}>
              <div className={styles.money}>
                <span>현재 보유 금액:</span>
                <span>{existMoney.toLocaleString()}원</span>
                <br />
                <span>결제 후 보유 금액:</span>
                <span>{afterMoney.toLocaleString()}원</span>
              </div>
              <div>
                <h3 style={{ marginTop: '20px' }}>일일 1시간 이용권</h3>
                <div style={{ display: 'flex', alignItems: 'baseline' }}>
                  <p> 결제금액: </p>
                  <span className={styles.number}>
                    {ticketPrice.toLocaleString()}원
                  </span>
                </div>
              </div>

              <div style={{ marginTop: '20px', fontSize: '1.15rem' }}>
                <Checkbox
                  checked={checked}
                  onChange={handleChange}
                  inputProps={{ 'aria-label': 'controlled' }}
                  sx={{ margin: '0px' }}
                />
                추가요금자동결제, 환불규정, 이용약관에 동의하며 결제를
                진행합니다.
              </div>
              {isLackOfMoney && (
                <div style={{ display: 'flex', alignItems: 'center' }}>
                  {lackNotice}
                  <Button
                    variant="contained"
                    color="primary"
                    sx={{ fontSize: '1rem', height: '5vh' }}
                    onClick={movePageHandler}
                  >
                    충전 페이지로 이동
                  </Button>
                </div>
              )}
              <div className={styles.inputBox}>
                <Button
                  variant="contained"
                  color="primary"
                  sx={{ fontSize: '1.3rem' }}
                  disabled={!checked || isLackOfMoney}
                  onClick={onSubmitHandler}
                >
                  이용권 결제
                </Button>
              </div>
            </div>
          </Card>
        </Container>
      </Article>
    </Layout>
  );
};

export default UserTicketBuy;
