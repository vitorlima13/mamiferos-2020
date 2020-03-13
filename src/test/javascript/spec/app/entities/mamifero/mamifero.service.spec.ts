import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MamiferoService } from 'app/entities/mamifero/mamifero.service';
import { IMamifero, Mamifero } from 'app/shared/model/mamifero.model';
import { TipoMamifero } from 'app/shared/model/enumerations/tipo-mamifero.model';

describe('Service Tests', () => {
  describe('Mamifero Service', () => {
    let injector: TestBed;
    let service: MamiferoService;
    let httpMock: HttpTestingController;
    let elemDefault: IMamifero;
    let expectedResult: IMamifero | IMamifero[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MamiferoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Mamifero(0, 'AAAAAAA', TipoMamifero.GATO, 'AAAAAAA', 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Mamifero', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Mamifero()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Mamifero', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            tipo: 'BBBBBB',
            apelido: 'BBBBBB',
            numero: 1,
            altura: 1,
            peso: 1,
            comprimento: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Mamifero', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            tipo: 'BBBBBB',
            apelido: 'BBBBBB',
            numero: 1,
            altura: 1,
            peso: 1,
            comprimento: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Mamifero', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
