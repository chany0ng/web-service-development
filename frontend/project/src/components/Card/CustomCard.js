import styles from './CustomCard.module.scss';
import { Container, Card, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const CustomCard = ({ info }) => {
  const navigate = useNavigate();

  return (
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
        <div className={styles.cardHeader}>
          <span>
            <h1>{info.user_id}</h1> 회원님
          </span>
          <Button
            onClick={() => {
              navigate('/user/info/edit');
            }}
            variant="contained"
            color="primary"
            sx={{ fontSize: '1rem' }}
          >
            내 정보 관리 {`>`}
          </Button>
        </div>
        <div className={styles.cardBody}>
          <div className={styles.cardMoney}>
            <div>현재 보유중인 금액</div>
            <h3>{info.cash}원</h3>
            <Button
              onClick={() => {
                navigate('/user/payment/charge');
              }}
              variant="contained"
              color="primary"
              sx={{ fontSize: '1rem', marginLeft: '2rem' }}
            >
              충전하기 {`>`}
            </Button>
          </div>
          <div className={styles.cardMoney}>
            <div>현재 대여중인 따릉이</div>
            {info.rented ? <h3>bike id: {info.bike_id}</h3> : <h3>없음</h3>}
            <Button
              onClick={() => {
                navigate('/user/tickets/purchase');
              }}
              variant="contained"
              color="primary"
              sx={{ fontSize: '1rem' }}
            >
              이용권 구매 {`>`}
            </Button>
          </div>
        </div>
      </Card>
    </Container>
  );
};

export default CustomCard;
