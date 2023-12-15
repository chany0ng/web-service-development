import Article from '../../layouts/Article';
import Layout from '../../layouts/Layout';
import styles from '../Admin/Notice/AdminNoticeCreate.module.scss';
import { useState } from 'react';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { postFetch } from '../../config';
const BoardCreate = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleTitleChange = (event) => {
    setTitle((prev) => event.target.value);
  };

  const handleContentChange = (event) => {
    setContent(event.target.value);
  };

  const onSubmitHandler = async (e) => {
    try {
      e.preventDefault();
      const response = await postFetch('api/board/create', {
        title: title,
        content: content
      });
      if (response.status === 200) {
        navigate('/board/list/1');
      } else if (response.status === 401) {
        navigate('/');
      } else {
        throw new Error('자유게시판 글 작성 에러');
      }
    } catch (error) {
      console.error(error);
      alert(error);
    }
  };
  return (
    <Layout admin="true">
      <h1 style={{ fontSize: '2.5rem', marginBottom: '5vh' }}>
        게시판 글 작성
      </h1>
      <Article>
        <div className={styles['notice-form']}>
          <table className={styles.table}>
            <colgroup>
              <col width="25%" />
              <col width="75%" />
            </colgroup>

            <tbody>
              <tr>
                <td className={styles.col1}>제목: </td>
                <td className={styles.cell}>
                  <input
                    type="text"
                    id="title"
                    value={title}
                    style={{ height: '35px', width: '100%' }}
                    onChange={handleTitleChange}
                  />
                </td>
              </tr>
              <tr>
                <td className={styles.col1}>내용: </td>
                <td className={styles.cell}>
                  <textarea
                    id="content"
                    value={content}
                    onChange={handleContentChange}
                    rows="15" // 원하는 행 수로 조절
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <div>
            <Button
              onClick={onSubmitHandler}
              type="submit"
              variant="contained"
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
              작성하기
            </Button>
          </div>
        </div>
      </Article>
    </Layout>
  );
};

export default BoardCreate;
