import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Address } from '../model/address-model';
import { User } from '../model/user-model';

const httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Methods': '*'})
};

@Injectable({
  providedIn: 'root'
})

export class AddressService {

  private addressList = 'http://localhost:8090/address';

  constructor(private http: HttpClient) { }

  getAddressBoard(): Observable<Address[]> {
    return this.http.get<Address[]>(this.addressList, httpOptions);
  }

  getAddressById(id: number): Observable<Address> {
    return this.http.get<Address>(this.addressList + '/' + id, httpOptions);
  }

  createAddress(address: Address): Observable<Address> {
    return this.http.post<Address>(this.addressList, address, httpOptions);
  }

  updateAddress(address: Address): Observable<Address> {
    return this.http.put<Address>(this.addressList + '/' + address.id, address, httpOptions);
  }

  deleteAddress(id: number): Observable<any> {
    return this.http.delete<any>(this.addressList + '/' + id, httpOptions);
  }

  deleteAddressByUser(id: number): Observable<any> {
    return this.http.delete<any>(this.addressList + '/deleteByUser/' + id, httpOptions);
  }
}
