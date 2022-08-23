import { ICart, NewCart } from './cart.model';

export const sampleWithRequiredData: ICart = {
  id: 50053,
};

export const sampleWithPartialData: ICart = {
  id: 85678,
};

export const sampleWithFullData: ICart = {
  id: 91768,
};

export const sampleWithNewData: NewCart = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
