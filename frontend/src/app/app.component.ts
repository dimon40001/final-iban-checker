import { Component } from '@angular/core';
import { IbanValidationServiceService } from "./iban-validation-service.service";
import { Iban } from "./Iban";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'IBAN Validator';

  ibans = '';

  currentFilter = 'select filter';

  validationResults: Iban[] = [];

  oldResults: Iban[] = [];


  constructor(private ibanValidator: IbanValidationServiceService) {
  }

  validate() {
    let ibansList = this.ibans
      .trim()
      .replace(" ", "")
      .split("\n")
      .map(str => new Iban(str))
    this.ibanValidator.validate(ibansList)
      .subscribe(data => {
        this.validationResults = data;
      });
  }

  getOld(filter: string) {
    this.currentFilter = filter;
    this.ibanValidator.getOld(filter)
      .subscribe(data => {
        this.oldResults = data;
      });
  }
}
