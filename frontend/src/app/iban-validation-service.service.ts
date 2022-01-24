import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { Iban } from "./Iban";
import { environment } from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class IbanValidationServiceService {

  constructor(private http: HttpClient) {
  }

  validate(ibansList: Iban[]) {
    return this.http.post<Iban[]>(environment.url + "/validate", ibansList);
  }

  getOld(filter: string) {
    const queryParams = new HttpParams()
      .set('filter', filter);
    return this.http.get<Iban[]>(environment.url, {params: queryParams});
  }
}

