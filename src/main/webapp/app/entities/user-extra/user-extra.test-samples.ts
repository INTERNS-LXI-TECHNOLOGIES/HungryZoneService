import { IUserExtra, NewUserExtra } from './user-extra.model';

export const sampleWithRequiredData: IUserExtra = {
  id: 70386,
  phoneNumber: 'withdrawal',
  address: 'THX Pennsylvania array',
  locationAtXAxis: 'capacitor Cotton Money',
  locationAtYAxis: '1080p primary',
};

export const sampleWithPartialData: IUserExtra = {
  id: 89414,
  phoneNumber: 'Cheese payment',
  address: 'scalable mission-critical Pizza',
  locationAtXAxis: 'Analyst Mississippi',
  locationAtYAxis: 'Liaison Concrete',
};

export const sampleWithFullData: IUserExtra = {
  id: 51330,
  phoneNumber: 'application Analyst withdrawal',
  address: 'frame infrastructures calculating',
  locationAtXAxis: 'Frozen Avon Infrastructure',
  locationAtYAxis: 'Fresh',
};

export const sampleWithNewData: NewUserExtra = {
  phoneNumber: 'orange card',
  address: 'withdrawal architectures',
  locationAtXAxis: 'orchid Granite Unbranded',
  locationAtYAxis: '1080p Fantastic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
