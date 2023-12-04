import { Pagination } from '@mui/material';
const CustomPagination = ({ page, totalPages, setPage }) => {
  const count = Math.floor(totalPages / 10) + 1;
  return (
    <Pagination
      page={page}
      count={count}
      onChange={setPage}
      color="success"
      size="large"
      sx={{
        padding: '20px',
        '& ul': {
          justifyContent: 'center'
        }
      }}
    />
  );
};
export default CustomPagination;
