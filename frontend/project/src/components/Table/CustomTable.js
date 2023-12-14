import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useContext } from 'react';
import { MyContext } from './../../pages/User/UserNotice/UserNoticeList';
import { MyContext2 } from '../../pages/Admin/Notice/AdminNoticeList';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
    fontSize: 13
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14
  }
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0
  }
}));

const CustomTable = ({ headData, bodyData }) => {
  const onClickPostHandler = useContext(MyContext);
  const onClickPostHandler2 = useContext(MyContext2);

  const clickHandler = (row) => {
    if (onClickPostHandler) {
      onClickPostHandler(row);
    } else if (onClickPostHandler2) {
      onClickPostHandler2(row);
    }
  };
  return (
    <TableContainer component={Paper} sx={{ marginTop: '30px' }}>
      <Table sx={{ minWidth: '50vw' }} aria-label="customized table">
        <TableHead>
          <TableRow>
            {headData.map((data, index) => (
              <StyledTableCell key={index} align="center">
                {data}
              </StyledTableCell>
            ))}
          </TableRow>
        </TableHead>
        {bodyData && bodyData.length > 0 ? (
          <TableBody>
            {bodyData.map((row, index) => (
              <StyledTableRow
                key={index}
                sx={{ cursor: 'pointer' }}
                onClick={() => {
                  clickHandler(row);
                }}
              >
                {Object.keys(row)
                  .filter((key) => key !== 'notice_id') // notice_id를 제외한 키만 필터링
                  .map((key, cellIndex) => (
                    <StyledTableCell key={cellIndex} align="center">
                      {row[key]}
                    </StyledTableCell>
                  ))}
              </StyledTableRow>
            ))}
          </TableBody>
        ) : (
          <TableBody>
            <StyledTableRow>
              <StyledTableCell
                colSpan="10"
                align="center"
                sx={{ color: 'red', fontSize: '5rem' }}
              >
                데이터가 없습니다
              </StyledTableCell>
            </StyledTableRow>
          </TableBody>
        )}
      </Table>
    </TableContainer>
  );
};

export default CustomTable;
