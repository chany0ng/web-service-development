import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

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
  // 현재 예시 데이터 사용중
  console.log(headData);
  return (
    <TableContainer component={Paper} sx={{ marginTop: '50px' }}>
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
        <TableBody>
          {bodyData.map((row, index) => (
            <StyledTableRow key={index}>
              {Object.values(row).map((cellValue, cellIndex) => (
                <StyledTableCell key={cellIndex} align="center">
                  {cellValue}
                </StyledTableCell>
              ))}
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default CustomTable;
