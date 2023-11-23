import Layout from '../../../layouts/Layout';
import TabBar from '../../../components/TabBar/TabBar';
import InnerTabBar from '../../../components/TabBar/InnerTabBar';
import { useState } from 'react';
import styles from './UserInfoEdit.module.scss';
import { postData } from '../../../config';
import { Grid, Alert, Button } from '@mui/material';
import CheckOutlinedIcon from '@mui/icons-material/CheckOutlined';

const innerTitle = ['개인정보 수정', '대여소 즐겨찾기'];
const outerTab = 'info';
const innerTab = 'edit';
const UserInfoEdit = () => {
  const [inputData, setInputData] = useState({
    email: 'sibal@naver.com',
    user_id: 'abc123',
    password: '',
    passwordCheck: '',
    phone_number: '01033289942'
  });
  const [isValid, setIsValid] = useState(true);
  const inputDataHandler = (e) => {
    e.preventDefault();
    const key = e.target.id;
    const value = e.target.value;
    setInputData((prevData) => ({ ...prevData, [key]: value }));
  };

  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();
      console.log(inputData.password.trim());
      if (
        inputData.user_id.trim() === '' ||
        inputData.password.trim() === '' ||
        inputData.phone_number.trim() === '' ||
        !inputData.email.includes('@')
      ) {
        setIsValid(false);
      } else {
        setIsValid(true);
        const { status } = await postData('url', inputData);
        if (status) {
          alert('개인정보 수정 완료!');
          // navigate('/user/info/edit');
        }
      }
    } catch (error) {
      console.error('개인정보 수정 에러', error);
    }
  };
  return (
    <Layout>
      <TabBar title={innerTitle} select={outerTab} />
      <InnerTabBar title={innerTitle} select={innerTab} />
      <article className={styles.article}>
        <div className={styles['flex-container']}>
          <table className={styles.table}>
            <colgroup>
              <col width="25%" />
              <col width="75%" />
            </colgroup>
            <tbody>
              <tr>
                <td className={styles.col1}>아이디</td>
                <td className={styles.cell}>{inputData.user_id}</td>
              </tr>
              <tr>
                <td className={styles.col1}>비밀번호</td>
                <td className={styles.cell}>
                  <input
                    type="password"
                    name="password"
                    id="password"
                    value={inputData.password}
                    onChange={inputDataHandler}
                  />
                  <span style={{ marginLeft: '10px', fontSize: '1rem' }}>
                    <CheckOutlinedIcon
                      sx={{
                        color: 'secondary.light',
                        fontSize: '15px',
                        paddingTop: '4px'
                      }}
                    />
                    20자 이하여야 합니다
                  </span>
                </td>
              </tr>
              <tr>
                <td className={styles.col1}>비밀번호 확인</td>
                <td className={styles.cell}>
                  <input
                    type="password"
                    name="passwordCheck"
                    id="passwordCheck"
                    value={inputData.passwordCheck}
                    onChange={inputDataHandler}
                  />
                </td>
              </tr>
              <tr>
                <td className={styles.col1}>이메일</td>
                <td className={`${styles.cell} ${styles.tdFlex}`}>
                  <input
                    type="email"
                    name="email"
                    id="email"
                    value={inputData.email}
                    onChange={inputDataHandler}
                  />
                  <span style={{ marginLeft: '10px', fontSize: '1rem' }}>
                    <CheckOutlinedIcon
                      sx={{
                        color: 'secondary.light',
                        fontSize: '15px',
                        paddingTop: '4px'
                      }}
                    />
                    @를 포함해야 합니다
                  </span>
                </td>
              </tr>
              <tr>
                <td className={styles.col1}>휴대폰 번호</td>
                <td className={styles.cell}>
                  <input
                    type="tel"
                    name="phone_number"
                    id="phone_number"
                    value={inputData.phone_number}
                    onChange={inputDataHandler}
                  />
                </td>
              </tr>
            </tbody>
          </table>
          {!isValid && (
            <Grid item xs={6} sx={{ color: 'error.main' }}>
              <Alert variant="outlined" severity="error">
                입력값을 다시 확인해주세요!
              </Alert>
            </Grid>
          )}
          <Button
            onClick={onSubmitHandler}
            type="submit"
            variant="contained"
            sx={{
              mt: 3,
              width: '20vw',
              backgroundColor: 'secondary.main',
              '&:hover': {
                backgroundColor: 'secondary.main'
              }
            }}
          >
            수정하기
          </Button>
        </div>
      </article>
    </Layout>
  );
};
export default UserInfoEdit;
