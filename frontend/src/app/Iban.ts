export class Iban {

  iban: string;
  valid: boolean;

  constructor(iban: string) {
    this.iban = iban;
    this.valid = false;
  }
}
