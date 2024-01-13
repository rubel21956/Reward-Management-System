export class forgetInfo {
    name: string;
    officeName: string;
    status: string;
    userId: string;
    resetRequired: string;
    reset: boolean;
  
    constructor(
      name: string,
      officeName: string,
      status: string,
      userId: string,
      resetRequired: string,
      reset: boolean
    ) {
      this.name = name;
      this.officeName = officeName;
      this.status = status;
      this.userId = userId;
      this.resetRequired = resetRequired;
      this.reset = reset;
    }

}
