import { TipoInseto } from 'app/shared/model/enumerations/tipo-inseto.model';

export interface IInseto {
  id?: number;
  tipo?: TipoInseto;
  numero?: number;
  altura?: number;
  peso?: number;
  comprimento?: number;
}

export class Inseto implements IInseto {
  constructor(
    public id?: number,
    public tipo?: TipoInseto,
    public numero?: number,
    public altura?: number,
    public peso?: number,
    public comprimento?: number
  ) {}
}
