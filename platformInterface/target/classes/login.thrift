namespace java com.finance.service.interfaces

 
service LoginService {
  string switchData(1:string username,2:string passWord );
}

service ThriftTestService {
  string switchData(1:string username,2:string passWord );
}