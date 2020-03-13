import { IMamifero } from 'app/shared/model/mamifero.model';

export interface ILoja {
  id?: number;
  nome?: string;
  mamiferos?: IMamifero[];
}

export class Loja implements ILoja {
  constructor(public id?: number, public nome?: string, public mamiferos?: IMamifero[]) {}
}
