import './App.css';
import Athentication from './views/athentication';

function App() {
  /*
  const [connection, setConnection] = useState<String>('');
  const connectionTest = () => {
    axios.get('http://localhost:8080/user/login/mark/mark').then((respose) => {
      setConnection(respose.data);
    }).catch((error) => {
      setConnection(error.message);
    })
  }

  useEffect(() => {
    connectionTest(); // 배열안의 특정한 상태가 바뀌면 실행
  }, []); // 배열을 비워놓으면 맨처음 한번만 실행
  스프링 부트 연동 테스트
*/
  return (
    <Athentication />
  );
}

export default App;
