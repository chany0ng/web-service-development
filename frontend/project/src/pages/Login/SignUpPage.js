import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { MenuItem } from '@mui/material';
import LoginBackground from '../../components/Background/LoginBackground';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { postFetch } from '../../config';
import { Alert } from '@mui/material';
import { loginPageAuthCheck } from '../../AuthCheck';

const pwQuestionList = [
  {
    question: 'elementary',
    description: '졸업한 초등학교 이름',
    value: 1
  },
  {
    question: 'middle',
    description: '졸업한 중학교 이름',
    value: 2
  }
];

const SignUpPage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    loginPageAuthCheck(navigate);
  }, []);
  const [inputData, setInputData] = useState({
    id: '',
    password: '',
    passwordCheck: '',
    pw_question: 1,
    pw_answer: '',
    email: '',
    phone_number: '',
    role: 'user'
  });
  const [isValid, setIsValid] = useState(true);
  const [errorMsg, setErrorMsg] = useState('입력을 확인해주세요!');
  const inputDataHandler = (e) => {
    e.preventDefault();
    const key = e.target.id || 'pw_question';
    const value = e.target.value;
    setInputData((prevData) => ({ ...prevData, [key]: value }));
  };
  const checkUserInput = () => {
    if (
      inputData.id.trim() === '' ||
      inputData.email.trim() === '' ||
      inputData.password.trim() === '' ||
      inputData.passwordCheck.trim() === '' ||
      inputData.pw_answer.trim() === '' ||
      inputData.phone_number.trim() === ''
    ) {
      setErrorMsg('입력하지 않은 값이 존재합니다');
    } else if (inputData.passwordCheck.trim() !== inputData.password.trim()) {
      setErrorMsg('비밀번호 동일여부를 확인해주세요');
    } else if (!inputData.email.includes('@')) {
      setErrorMsg('이메일에 @를 포함해주세요');
    } else if (!/^\d+$/.test(inputData.phone_number)) {
      setErrorMsg('전화번호는 숫자만 입력해주세요');
    } else {
      return true;
    }
  };
  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();
      if (!checkUserInput()) {
        setIsValid(false);
      } else {
        setIsValid(true);
        const finalInputData = { ...inputData };
        delete finalInputData.passwordCheck;
        const response = await postFetch('api/auth/signup', finalInputData);
        if (response.status === 200) {
          alert('회원가입 성공');
          navigate('/signin');
        }
        if (response.status === 500) {
          alert('이미 존재하는 ID입니다!');
        }
      }
    } catch (error) {
      console.error('회원가입 에러', error);
      alert(error);
    }
  };
  return (
    <LoginBackground>
      <Container component="main" sx={{ marginTop: '150px', width: '50vw' }}>
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
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h2" variant="h4">
            회원 가입
          </Typography>
          <Box component="form" noValidate sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  value={inputData.id}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="id"
                  id="id"
                  label="Id"
                  type="id"
                  variant="standard"
                  helperText="20자 이하"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.password}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  variant="standard"
                  helperText="20자 이하"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.passwordCheck}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="passwordCheck"
                  label="Password Check"
                  type="password"
                  id="passwordCheck"
                  variant="standard"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.pw_question}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  name="pw_question"
                  id="pw_question"
                  select
                  label="Password Question"
                  variant="standard"
                  helperText="비밀번호 찾기 질문을 선택해주세요"
                >
                  {pwQuestionList.map((option) => (
                    <MenuItem key={option.value} value={option.value}>
                      {option.description}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.pw_answer}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  variant="standard"
                  name="pw_answer"
                  label="Password Answer"
                  id="pw_answer"
                  helperText="비밀번호 찾기 답변을 작성해주세요"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.email}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  variant="standard"
                  name="email"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  value={inputData.phone_number}
                  onChange={inputDataHandler}
                  required
                  fullWidth
                  variant="standard"
                  name="phone_number"
                  label="Phone Number"
                  type="tel"
                  id="phone_number"
                  helperText="( - 없이 숫자만 입력)"
                />
              </Grid>
              {!isValid && (
                <Grid
                  item
                  xs={8}
                  sx={{ color: 'error.main', margin: '0 auto' }}
                >
                  <Alert
                    variant="outlined"
                    severity="error"
                    sx={{ fontSize: '1.3rem' }}
                  >
                    {errorMsg}
                  </Alert>
                </Grid>
              )}
            </Grid>
            <Button
              onClick={onSubmitHandler}
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign Up
            </Button>
            <Grid container justifyContent="center">
              <Grid item xs={4}>
                <Link
                  to="/findpw"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  비밀번호를 잊으셨나요?
                </Link>
              </Grid>
              <Grid item xs={4}>
                <Link
                  to="/signin"
                  style={{ textDecoration: 'none', color: 'blue' }}
                >
                  이미 계정을 가지고 계신가요?
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </LoginBackground>
  );
};

export default SignUpPage;
