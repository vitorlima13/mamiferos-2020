import { Moment } from 'moment';

export interface IObservacaoAnimal {
  id?: number;
  data?: Moment;
  local?: string;
  comprimento?: number;
  altura?: number;
  peso?: number;
}

export class ObservacaoAnimal implements IObservacaoAnimal {
  constructor(
    public id?: number,
    public data?: Moment,
    public local?: string,
    public comprimento?: number,
    public altura?: number,
    public peso?: number
  ) {}
}
