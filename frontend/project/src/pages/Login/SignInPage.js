import { useState, useReducer } from 'react';
import './css/SignInPage.module.min.css';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { MenuItem } from '@mui/material';
import LoginBackground from '../../components/Background/LoginBackground';
import { postData } from '../../config';
import { useNavigate } from 'react-router-dom';

const passwordQuestion = [
  {
    question: 'school',
    description: '관리자'
  },
  {
    question: 'school2',
    description: '이용자'
  }
];

const SignInPage = () => {
  const navigate = useNavigate();
  const [inputData, setInputData] = useState({
    user_id: '',
    password: '',
    userType: ''
  });
  const [isValid, setIsValid] = useState(true);
  const inputDataHandler = (e) => {
    e.preventDefault();
    const key = e.target.id || 'userType';
    const value = e.target.value;
    setInputData((prevData) => ({ ...prevData, [key]: value }));
  };

  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();
      if (inputData.user_id.trim() === '' || inputData.password.trim() === '') {
        setIsValid(false);
      } else {
        setIsValid(true);
        const { status, data } = await postData('url', inputData);
        if (status) {
          localStorage.setItem('token', data.token);
          // 로컬스토리지에 로그인상태 저장 -> 메인화면에서 useEffect로 받기
          // navigate('/signin');
        }
      }
    } catch (error) {
      console.error('로그인버튼 에러', error);
    }
  };

  return (
    <LoginBackground>
      <Container component="main" maxWidth="sm">
        <Box
          sx={{
            margin: 5,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center'
          }}
          style={{
            padding: '20px',
            border: '1px solid black',
            borderRadius: '10px',
            backgroundColor: '#ffffff'
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'primary.main' }}>
            <LoginOutlinedIcon fontSize="large" />
          </Avatar>
          <Typography component="h2" variant="h4">
            로그인
          </Typography>
          <Box component="form" noValidate sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  value={inputData.user_id}
                  onChange={inputDataHandler}
                  fullWidth
                  name="user_id"
                  label="Id"
                  type="id"
                  id="user_id"
                  variant="standard"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.password}
                  fullWidth
                  onChange={inputDataHandler}
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  variant="standard"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  id="userType"
                  select
                  label="User type"
                  variant="standard"
                  helperText="관리자 or 이용자"
                  value={inputData.userType}
                  onChange={inputDataHandler}
                >
                  {passwordQuestion.map((option) => (
                    <MenuItem
                      id="userType"
                      key={option.question}
                      value={option.description}
                    >
                      {option.description}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>
              {!isValid && (
                <Grid item xs={12} sx={{ color: 'error.main' }}>
                  입력을 다시 확인해주세요!
                </Grid>
              )}
            </Grid>
            <Button
              type="submit"
              onClick={onSubmitHandler}
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Login
            </Button>
            <Grid container justifyContent="center">
              <Grid item xs={4}>
                <Link
                  to="/signup"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  회원가입
                </Link>
              </Grid>
              <Grid item xs={4}>
                <Link
                  to="/findpw"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  비밀번호 찾기
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </LoginBackground>
  );
};

export default SignInPage;
