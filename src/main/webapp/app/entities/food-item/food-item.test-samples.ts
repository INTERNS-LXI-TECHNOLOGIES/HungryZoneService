import { IFoodItem, NewFoodItem } from './food-item.model';

export const sampleWithRequiredData: IFoodItem = {
  id: 55365,
  quantity: 37862,
  unit: 'payment Ball Liberian',
};

export const sampleWithPartialData: IFoodItem = {
  id: 89339,
  quantity: 1326,
  unit: 'calculate Industrial infomediaries',
};

export const sampleWithFullData: IFoodItem = {
  id: 4064,
  quantity: 74931,
  unit: 'Rustic Concrete',
};

export const sampleWithNewData: NewFoodItem = {
  quantity: 54344,
  unit: 'Cambridgeshire',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
