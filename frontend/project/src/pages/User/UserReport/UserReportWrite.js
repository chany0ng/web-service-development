import Layout from '../../../layouts/Layout';
import Article from './../../../layouts/Article';
import InnerTabBar from './../../../components/TabBar/InnerTabBar';
import CustomButton from '../../../components/Button/CustomButton';
import { useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { useState, useEffect } from 'react';
import { postFetch } from '../../../config';
import { TextField } from '@mui/material';
import Checkbox from '@mui/material/Checkbox';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '1px solid #000',
  boxShadow: 24,
  p: 4
};
const innerTitle = ['공지사항', '고장 신고'];
const innerTab = 'report';
const url = {
  notice: '/user/notice/noticeList/1',
  report: '/user/report/reportList/1'
};
const UserReportWrite = () => {
  const navigate = useNavigate();
  const [checked, setChecked] = useState({
    타이어: false,
    체인: false,
    안장: false,
    페달: false,
    단말기: false
  });
  const handleChange = (event) => {
    const key = event.target.id;
    const value = event.target.checked;
    setChecked((prev) => ({
      ...prev,
      [key]: value
    }));
  };
  const [open, setOpen] = useState(false);
  const handleOpen = async () => {
    await fetchPost();
  };
  const handleClose = () => {
    setOpen(false);
    navigate('/user/notice/noticeList/1');
  };
  const [reportData, setReportData] = useState({
    bike_id: ''
  });
  const reportDataHandler = (event) => {
    setReportData((prev) => ({
      ...prev,
      bike_id: event.target.value
    }));
  };
  const fetchPost = async () => {
    try {
      const finalData = { ...reportData, ...checked };
      const response = await postFetch('api/report', finalData);
      if (response.status === 200) {
        setOpen(true);
      } else if (response.status === 401) {
        navigate('/');
      } else if (response.status === 400 || response.status === 500) {
        const data = await response.json();
        alert(data.message);
      } else {
        throw new Error(`게시물 정보 로드 실패: ${response.status}`);
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  return (
    <Layout>
      <InnerTabBar title={innerTitle} select={innerTab} url={url} />
      <Article>
        <h1
          style={{ textAlign: 'left', fontSize: '3rem', marginBottom: '5vh' }}
        >
          고장 신고
        </h1>
        <h2 style={{ margin: '5px', padding: '10px', textAlign: 'left' }}>
          자전거 번호 입력
        </h2>
        <TextField
          label="자전거 번호"
          required
          value={reportData.bike_id}
          onChange={reportDataHandler}
          id="outlined-required"
          placeholder="자전거 고유 번호 입력"
          variant="standard"
          margin="normal"
          sx={{
            '& input': { width: '15vw', height: '5vh', fontSize: '1.5rem' }
          }}
        />

        <h2
          style={{
            marginTop: '20px',
            padding: '10px',
            paddingTop: '20px',
            borderTop: '1px solid black',
            textAlign: 'left'
          }}
        >
          신고 할 부품 선택
        </h2>
        <FormGroup
          sx={{
            width: 'fit-content',
            flexDirection: 'row',
            margin: '0 auto',
            marginBottom: '5vh'
          }}
        >
          <FormControlLabel
            control={
              <Checkbox
                inputProps={{ 'aria-label': 'controlled' }}
                id="타이어"
                checked={checked.타이어}
                onChange={handleChange}
                sx={{ '& .MuiSvgIcon-root': { fontSize: 25 } }}
              />
            }
            label={
              <Typography sx={{ fontSize: 15 /* 원하는 폰트 크기 */ }}>
                타이어
              </Typography>
            }
          />
          <FormControlLabel
            control={
              <Checkbox
                inputProps={{ 'aria-label': 'controlled' }}
                id="체인"
                checked={checked.체인}
                onChange={handleChange}
                sx={{ '& .MuiSvgIcon-root': { fontSize: 25 } }}
              />
            }
            label={
              <Typography sx={{ fontSize: 15 /* 원하는 폰트 크기 */ }}>
                체인
              </Typography>
            }
          />
          <FormControlLabel
            control={
              <Checkbox
                inputProps={{ 'aria-label': 'controlled' }}
                id="안장"
                checked={checked.안장}
                onChange={handleChange}
                sx={{ '& .MuiSvgIcon-root': { fontSize: 25 } }}
              />
            }
            label={
              <Typography sx={{ fontSize: 15 /* 원하는 폰트 크기 */ }}>
                안장
              </Typography>
            }
          />
          <FormControlLabel
            control={
              <Checkbox
                inputProps={{ 'aria-label': 'controlled' }}
                id="페달"
                checked={checked.페달}
                onChange={handleChange}
                sx={{ '& .MuiSvgIcon-root': { fontSize: 25 } }}
              />
            }
            label={
              <Typography sx={{ fontSize: 15 /* 원하는 폰트 크기 */ }}>
                페달
              </Typography>
            }
          />
          <FormControlLabel
            control={
              <Checkbox
                inputProps={{ 'aria-label': 'controlled' }}
                id="단말기"
                checked={checked.단말기}
                onChange={handleChange}
                sx={{ '& .MuiSvgIcon-root': { fontSize: 25 } }}
              />
            }
            label={
              <Typography sx={{ fontSize: 15 /* 원하는 폰트 크기 */ }}>
                단말기
              </Typography>
            }
          />
        </FormGroup>
        <CustomButton name="고장 신고 하기" onClick={handleOpen} />
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Typography id="modal-modal-title" variant="h4" component="h2">
              고장신고가 접수되었습니다
            </Typography>
            <Typography
              id="modal-modal-description"
              sx={{ mt: 2 }}
              variant="h5"
            >
              고장 신고 내용 :
              {Object.keys(checked)
                .filter((key) => checked[key])
                .join(', ')}
            </Typography>
          </Box>
        </Modal>
      </Article>
    </Layout>
  );
};

export default UserReportWrite;
