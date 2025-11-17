package com.meuprojetotcc.autenticacao_diplomas.contracts.src.main.java.com.meuprojetotcc.autenticacao_diplomas.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;

import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class DiplomaRegistry extends Contract {
    public static final String BINARY = "608060405234801561000f575f5ffd5b5060405161273c38038061273c83398181016040528101906100319190610238565b6100435f5f1b8261007b60201b60201c565b506100747fdf8b4c520ffe197c5343c6f5aec59570151ef9a492f2c624fd45ddde6135ec428261007b60201b60201c565b5050610263565b5f61008c838361017060201b60201c565b6101665760015f5f8581526020019081526020015f205f015f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff0219169083151502179055506101036101d360201b60201c565b73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16847f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a46001905061016a565b5f90505b92915050565b5f5f5f8481526020019081526020015f205f015f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16905092915050565b5f33905090565b5f5ffd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f610207826101de565b9050919050565b610217816101fd565b8114610221575f5ffd5b50565b5f815190506102328161020e565b92915050565b5f6020828403121561024d5761024c6101da565b5b5f61025a84828501610224565b91505092915050565b6124cc806102705f395ff3fe608060405234801561000f575f5ffd5b5060043610610114575f3560e01c806375b238fc116100a0578063a217fddf1161006f578063a217fddf146102ee578063a608220d1461030c578063cfb102a414610328578063d547741f1461035c578063debd30f31461037857610114565b806375b238fc1461025257806391d148541461027057806393d9ca46146102a057806397aba7f9146102be57610114565b806336568abe116100e757806336568abe146101b057806336e7cdd5146101cc578063374f3d97146101fc5780633f4462aa146102185780636a62b2081461023657610114565b806301ffc9a714610118578063248a9ca3146101485780632f2ff15d1461017857806333dae60614610194575b5f5ffd5b610132600480360381019061012d91906116e9565b6103a8565b60405161013f919061172e565b60405180910390f35b610162600480360381019061015d919061177a565b610421565b60405161016f91906117b4565b60405180910390f35b610192600480360381019061018d9190611827565b61043d565b005b6101ae60048036038101906101a99190611827565b61045f565b005b6101ca60048036038101906101c59190611827565b6104d1565b005b6101e660048036038101906101e19190611865565b61054c565b6040516101f391906117b4565b60405180910390f35b61021660048036038101906102119190611827565b610581565b005b6102206105f3565b60405161022d91906117b4565b60405180910390f35b610250600480360381019061024b91906119f1565b610617565b005b61025a610932565b60405161026791906117b4565b60405180910390f35b61028a60048036038101906102859190611827565b610956565b604051610297919061172e565b60405180910390f35b6102a86109b9565b6040516102b591906117b4565b60405180910390f35b6102d860048036038101906102d39190611a71565b6109dd565b6040516102e59190611ada565b60405180910390f35b6102f6610a1b565b60405161030391906117b4565b60405180910390f35b610326600480360381019061032191906119f1565b610a21565b005b610342600480360381019061033d9190611af3565b610c4b565b604051610353959493929190611ba9565b60405180910390f35b61037660048036038101906103719190611827565b610ed7565b005b610392600480360381019061038d9190611af3565b610ef9565b60405161039f919061172e565b60405180910390f35b5f7f7965db0b000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916148061041a575061041982611114565b5b9050919050565b5f5f5f8381526020019081526020015f20600101549050919050565b61044682610421565b61044f8161117d565b6104598383611191565b50505050565b7fdf8b4c520ffe197c5343c6f5aec59570151ef9a492f2c624fd45ddde6135ec426104898161117d565b6104938383610ed7565b7f6128b067553ef5486b77d6d6500eb7272c2219ab6a8c0f0c58c1b103cdb4e70d83836040516104c4929190611c01565b60405180910390a1505050565b6104d961127a565b73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff161461053d576040517f6697b23200000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b6105478282611281565b505050565b5f83838360405160200161056293929190611c28565b6040516020818303038152906040528051906020012090509392505050565b7fdf8b4c520ffe197c5343c6f5aec59570151ef9a492f2c624fd45ddde6135ec426105ab8161117d565b6105b5838361043d565b7f1c9e2fab282a5aa98846dbc9e5b1e546bee115dd80f86fe8ae96d32eb997199b83836040516105e6929190611c01565b60405180910390a1505050565b7f8b1b6d33bb4da2744c0f6fad78931a89085e155029a4f35fe9a6ed740c81817681565b7f338fcb7ee1ed07890c54f584c0fbff4af8bdbca0b7122870b0b370283d88e0646106418161117d565b5f5f1b8503610685576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161067c90611cb7565b60405180910390fd5b5f5f1b84036106c9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106c090611d1f565b60405180910390fd5b5f5f1b830361070d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161070490611d87565b60405180910390fd5b60015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8681526020019081526020015f206005015f9054906101000a900460ff16156107a9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107a090611def565b60405180910390fd5b6040518060c001604052808581526020018481526020018381526020013373ffffffffffffffffffffffffffffffffffffffff1681526020014281526020016001151581525060015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8781526020019081526020015f205f820151815f0155602082015181600101556040820151816002019081610862919061200a565b506060820151816003015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506080820151816004015560a0820151816005015f6101000a81548160ff0219169083151502179055509050503373ffffffffffffffffffffffffffffffffffffffff167f9c803cc12e91741547db336482839a1c499e3ff292eb58790135a1a94556e7bc86868633426040516109239594939291906120d9565b60405180910390a25050505050565b7fdf8b4c520ffe197c5343c6f5aec59570151ef9a492f2c624fd45ddde6135ec4281565b5f5f5f8481526020019081526020015f205f015f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f9054906101000a900460ff16905092915050565b7f338fcb7ee1ed07890c54f584c0fbff4af8bdbca0b7122870b0b370283d88e06481565b5f5f836040516020016109f0919061219e565b604051602081830303815290604052805190602001209050610a12818461136a565b91505092915050565b5f5f1b81565b7f338fcb7ee1ed07890c54f584c0fbff4af8bdbca0b7122870b0b370283d88e064610a4b8161117d565b338560015f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8281526020019081526020015f206005015f9054906101000a900460ff16610ae8576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610adf9061220d565b60405180910390fd5b5f5f1b8603610b2c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b2390612275565b60405180910390fd5b5f5f1b8503610b70576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b67906122dd565b60405180910390fd5b5f60015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8981526020019081526020015f20905086815f018190555085816001018190555084816002019081610be1919061200a565b504281600401819055503373ffffffffffffffffffffffffffffffffffffffff167fbfc5014d655e64bef413b35eb9b08ca9b0c59593abdbfbe0d7f65c09a719c1768989893342604051610c399594939291906120d9565b60405180910390a25050505050505050565b5f5f60605f5f5f60015f8973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8881526020019081526020015f206040518060c00160405290815f820154815260200160018201548152602001600282018054610ccc90611e3a565b80601f0160208091040260200160405190810160405280929190818152602001828054610cf890611e3a565b8015610d435780601f10610d1a57610100808354040283529160200191610d43565b820191905f5260205f20905b815481529060010190602001808311610d2657829003601f168201915b50505050508152602001600382015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160048201548152602001600582015f9054906101000a900460ff16151515158152505090508060a00151610e0b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e0290612345565b60405180910390fd5b610e357f8b1b6d33bb4da2744c0f6fad78931a89085e155029a4f35fe9a6ed740c81817633610956565b80610e6b57508773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b610eaa576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ea1906123ad565b60405180910390fd5b805f0151816020015182604001518360600151846080015195509550955095509550509295509295909350565b610ee082610421565b610ee98161117d565b610ef38383611281565b50505050565b5f5f60015f8573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8481526020019081526020015f206040518060c00160405290815f820154815260200160018201548152602001600282018054610f7590611e3a565b80601f0160208091040260200160405190810160405280929190818152602001828054610fa190611e3a565b8015610fec5780601f10610fc357610100808354040283529160200191610fec565b820191905f5260205f20905b815481529060010190602001808311610fcf57829003601f168201915b50505050508152602001600382015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160048201548152602001600582015f9054906101000a900460ff16151515158152505090508060a001516110b4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016110ab90612345565b60405180910390fd5b5f6110c7825f015183602001518661054c565b90505f6110d88284604001516109dd565b90508573ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614935050505092915050565b5f7f01ffc9a7000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916827bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916149050919050565b61118e8161118961127a565b611394565b50565b5f61119c8383610956565b6112705760015f5f8581526020019081526020015f205f015f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff02191690831515021790555061120d61127a565b73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16847f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a460019050611274565b5f90505b92915050565b5f33905090565b5f61128c8383610956565b15611360575f5f5f8581526020019081526020015f205f015f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f6101000a81548160ff0219169083151502179055506112fd61127a565b73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16847ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b60405160405180910390a460019050611364565b5f90505b92915050565b5f5f5f5f61137886866113e5565b925092509250611388828261143a565b82935050505092915050565b61139e8282610956565b6113e15780826040517fe2517d3f0000000000000000000000000000000000000000000000000000000081526004016113d89291906123cb565b60405180910390fd5b5050565b5f5f5f6041845103611425575f5f5f602087015192506040870151915060608701515f1a90506114178882858561159c565b955095509550505050611433565b5f600285515f1b9250925092505b9250925092565b5f600381111561144d5761144c6123f2565b5b8260038111156114605761145f6123f2565b5b0315611598576001600381111561147a576114796123f2565b5b82600381111561148d5761148c6123f2565b5b036114c4576040517ff645eedf00000000000000000000000000000000000000000000000000000000815260040160405180910390fd5b600260038111156114d8576114d76123f2565b5b8260038111156114eb576114ea6123f2565b5b0361152f57805f1c6040517ffce698f7000000000000000000000000000000000000000000000000000000008152600401611526919061241f565b60405180910390fd5b600380811115611542576115416123f2565b5b826003811115611555576115546123f2565b5b0361159757806040517fd78bce0c00000000000000000000000000000000000000000000000000000000815260040161158e91906117b4565b60405180910390fd5b5b5050565b5f5f5f7f7fffffffffffffffffffffffffffffff5d576e7357a4501ddfe92f46681b20a0845f1c11156115d8575f600385925092509250611679565b5f6001888888886040515f81526020016040526040516115fb9493929190612453565b6020604051602081039080840390855afa15801561161b573d5f5f3e3d5ffd5b5050506020604051035190505f73ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361166c575f60015f5f1b93509350935050611679565b805f5f5f1b935093509350505b9450945094915050565b5f604051905090565b5f5ffd5b5f5ffd5b5f7fffffffff0000000000000000000000000000000000000000000000000000000082169050919050565b6116c881611694565b81146116d2575f5ffd5b50565b5f813590506116e3816116bf565b92915050565b5f602082840312156116fe576116fd61168c565b5b5f61170b848285016116d5565b91505092915050565b5f8115159050919050565b61172881611714565b82525050565b5f6020820190506117415f83018461171f565b92915050565b5f819050919050565b61175981611747565b8114611763575f5ffd5b50565b5f8135905061177481611750565b92915050565b5f6020828403121561178f5761178e61168c565b5b5f61179c84828501611766565b91505092915050565b6117ae81611747565b82525050565b5f6020820190506117c75f8301846117a5565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6117f6826117cd565b9050919050565b611806816117ec565b8114611810575f5ffd5b50565b5f81359050611821816117fd565b92915050565b5f5f6040838503121561183d5761183c61168c565b5b5f61184a85828601611766565b925050602061185b85828601611813565b9150509250929050565b5f5f5f6060848603121561187c5761187b61168c565b5b5f61188986828701611766565b935050602061189a86828701611766565b92505060406118ab86828701611766565b9150509250925092565b5f5ffd5b5f5ffd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b611903826118bd565b810181811067ffffffffffffffff82111715611922576119216118cd565b5b80604052505050565b5f611934611683565b905061194082826118fa565b919050565b5f67ffffffffffffffff82111561195f5761195e6118cd565b5b611968826118bd565b9050602081019050919050565b828183375f83830152505050565b5f61199561199084611945565b61192b565b9050828152602081018484840111156119b1576119b06118b9565b5b6119bc848285611975565b509392505050565b5f82601f8301126119d8576119d76118b5565b5b81356119e8848260208601611983565b91505092915050565b5f5f5f5f60808587031215611a0957611a0861168c565b5b5f611a1687828801611766565b9450506020611a2787828801611766565b9350506040611a3887828801611766565b925050606085013567ffffffffffffffff811115611a5957611a58611690565b5b611a65878288016119c4565b91505092959194509250565b5f5f60408385031215611a8757611a8661168c565b5b5f611a9485828601611766565b925050602083013567ffffffffffffffff811115611ab557611ab4611690565b5b611ac1858286016119c4565b9150509250929050565b611ad4816117ec565b82525050565b5f602082019050611aed5f830184611acb565b92915050565b5f5f60408385031215611b0957611b0861168c565b5b5f611b1685828601611813565b9250506020611b2785828601611766565b9150509250929050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f611b6382611b31565b611b6d8185611b3b565b9350611b7d818560208601611b4b565b611b86816118bd565b840191505092915050565b5f819050919050565b611ba381611b91565b82525050565b5f60a082019050611bbc5f8301886117a5565b611bc960208301876117a5565b8181036040830152611bdb8186611b59565b9050611bea6060830185611acb565b611bf76080830184611b9a565b9695505050505050565b5f604082019050611c145f8301856117a5565b611c216020830184611acb565b9392505050565b5f606082019050611c3b5f8301866117a5565b611c4860208301856117a5565b611c5560408301846117a5565b949350505050565b5f82825260208201905092915050565b7f4e756d65726f20696e76616c69646f00000000000000000000000000000000005f82015250565b5f611ca1600f83611c5d565b9150611cac82611c6d565b602082019050919050565b5f6020820190508181035f830152611cce81611c95565b9050919050565b7f4861736820696e76616c69646f000000000000000000000000000000000000005f82015250565b5f611d09600d83611c5d565b9150611d1482611cd5565b602082019050919050565b5f6020820190508181035f830152611d3681611cfd565b9050919050565b7f436172696d626f20696e76616c69646f000000000000000000000000000000005f82015250565b5f611d71601083611c5d565b9150611d7c82611d3d565b602082019050919050565b5f6020820190508181035f830152611d9e81611d65565b9050919050565b7f4469706c6f6d61206a61207265676973747261646f00000000000000000000005f82015250565b5f611dd9601583611c5d565b9150611de482611da5565b602082019050919050565b5f6020820190508181035f830152611e0681611dcd565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f6002820490506001821680611e5157607f821691505b602082108103611e6457611e63611e0d565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f60088302611ec67fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611e8b565b611ed08683611e8b565b95508019841693508086168417925050509392505050565b5f819050919050565b5f611f0b611f06611f0184611b91565b611ee8565b611b91565b9050919050565b5f819050919050565b611f2483611ef1565b611f38611f3082611f12565b848454611e97565b825550505050565b5f5f905090565b611f4f611f40565b611f5a818484611f1b565b505050565b5b81811015611f7d57611f725f82611f47565b600181019050611f60565b5050565b601f821115611fc257611f9381611e6a565b611f9c84611e7c565b81016020851015611fab578190505b611fbf611fb785611e7c565b830182611f5f565b50505b505050565b5f82821c905092915050565b5f611fe25f1984600802611fc7565b1980831691505092915050565b5f611ffa8383611fd3565b9150826002028217905092915050565b61201382611b31565b67ffffffffffffffff81111561202c5761202b6118cd565b5b6120368254611e3a565b612041828285611f81565b5f60209050601f831160018114612072575f8415612060578287015190505b61206a8582611fef565b8655506120d1565b601f19841661208086611e6a565b5f5b828110156120a757848901518255600182019150602085019450602081019050612082565b868310156120c457848901516120c0601f891682611fd3565b8355505b6001600288020188555050505b505050505050565b5f60a0820190506120ec5f8301886117a5565b6120f960208301876117a5565b61210660408301866117a5565b6121136060830185611acb565b6121206080830184611b9a565b9695505050505050565b5f81905092915050565b7f19457468657265756d205369676e6564204d6573736167653a0a3332000000005f82015250565b5f612168601c8361212a565b915061217382612134565b601c82019050919050565b5f819050919050565b61219861219382611747565b61217e565b82525050565b5f6121a88261215c565b91506121b48284612187565b60208201915081905092915050565b7f4469706c6f6d61206e616f207265676973747261646f000000000000000000005f82015250565b5f6121f7601683611c5d565b9150612202826121c3565b602082019050919050565b5f6020820190508181035f830152612224816121eb565b9050919050565b7f4e6f766f206861736820696e76616c69646f00000000000000000000000000005f82015250565b5f61225f601283611c5d565b915061226a8261222b565b602082019050919050565b5f6020820190508181035f83015261228c81612253565b9050919050565b7f4e6f766f20636172696d626f20696e76616c69646f00000000000000000000005f82015250565b5f6122c7601583611c5d565b91506122d282612293565b602082019050919050565b5f6020820190508181035f8301526122f4816122bb565b9050919050565b7f4469706c6f6d61206e616f2065786973746500000000000000000000000000005f82015250565b5f61232f601283611c5d565b915061233a826122fb565b602082019050919050565b5f6020820190508181035f83015261235c81612323565b9050919050565b7f4e616f206175746f72697a61646f0000000000000000000000000000000000005f82015250565b5f612397600e83611c5d565b91506123a282612363565b602082019050919050565b5f6020820190508181035f8301526123c48161238b565b9050919050565b5f6040820190506123de5f830185611acb565b6123eb60208301846117a5565b9392505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602160045260245ffd5b5f6020820190506124325f830184611b9a565b92915050565b5f60ff82169050919050565b61244d81612438565b82525050565b5f6080820190506124665f8301876117a5565b6124736020830186612444565b61248060408301856117a5565b61248d60608301846117a5565b9594505050505056fea26469706673582212208dc295d82bd8a3531b58bc7d4123cb35208bd4d750fc7e9a1f93ad7b0cad0c8164736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADMIN_ROLE = "ADMIN_ROLE";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_EMISSOR_ROLE = "EMISSOR_ROLE";

    public static final String FUNC_MINISTERIO_ROLE = "MINISTERIO_ROLE";

    public static final String FUNC_ADICIONARROLE = "adicionarRole";

    public static final String FUNC_ATUALIZARDIPLOMA = "atualizarDiploma";

    public static final String FUNC_COMPUTEMESSAGEHASH = "computeMessageHash";

    public static final String FUNC_CONSULTARDIPLOMA = "consultarDiploma";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_RECOVERSIGNER = "recoverSigner";

    public static final String FUNC_REGISTRARDIPLOMA = "registrarDiploma";

    public static final String FUNC_REMOVERROLE = "removerRole";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_VERIFICARASSINATURA = "verificarAssinatura";


    public static final String ACCESSCONTROLBADCONFIRMATION_ERROR = "AccessControlBadConfirmation";
    ;

    public static final String ACCESSCONTROLUNAUTHORIZEDACCOUNT_ERROR = "AccessControlUnauthorizedAccount";
    ;

    public static final String ECDSAINVALIDSIGNATURE_ERROR = "ECDSAInvalidSignature";
    ;

    public static final String ECDSAINVALIDSIGNATURELENGTH_ERROR = "ECDSAInvalidSignatureLength";
    ;

    public static final String ECDSAINVALIDSIGNATURES_ERROR = "ECDSAInvalidSignatureS";
    ;

    public static final Event DIPLOMAATUALIZADO_EVENT = new Event("DiplomaAtualizado", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DIPLOMAREGISTRADO_EVENT = new Event("DiplomaRegistrado", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ROLEADICIONADA_EVENT = new Event("RoleAdicionada", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ROLEADMINCHANGED_EVENT = new Event("RoleAdminChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event ROLEGRANTED_EVENT = new Event("RoleGranted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ROLEREMOVIDA_EVENT = new Event("RoleRemovida", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ROLEREVOKED_EVENT = new Event("RoleRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected DiplomaRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DiplomaRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DiplomaRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DiplomaRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DiplomaAtualizadoEventResponse> getDiplomaAtualizadoEvents(TransactionReceipt transactionReceipt) {
        List<DiplomaAtualizadoEventResponse> responses = new ArrayList<>();

        // Itera sobre todos os logs do receipt
        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIPLOMAATUALIZADO_EVENT, log);

            // Se o log corresponde ao evento, adiciona à lista
            if (eventValues != null) {
                DiplomaAtualizadoEventResponse typedResponse = new DiplomaAtualizadoEventResponse();
                typedResponse.log = log;
                typedResponse.instituicao = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.numeroDiploma = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.novoHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.novoCarimboCid = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.atualizadoPor = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.atualizadoEm = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                responses.add(typedResponse);
            }
        }

        return responses;
    }


    public static DiplomaAtualizadoEventResponse getDiplomaAtualizadoEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIPLOMAATUALIZADO_EVENT, log);
        DiplomaAtualizadoEventResponse typedResponse = new DiplomaAtualizadoEventResponse();
        typedResponse.log = log;
        typedResponse.instituicao = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.numeroDiploma = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.novoHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.novoCarimboCid = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.atualizadoPor = (String) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.atualizadoEm = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
        return typedResponse;
    }

    public Flowable<DiplomaAtualizadoEventResponse> diplomaAtualizadoEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDiplomaAtualizadoEventFromLog(log));
    }

    public Flowable<DiplomaAtualizadoEventResponse> diplomaAtualizadoEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIPLOMAATUALIZADO_EVENT));
        return diplomaAtualizadoEventFlowable(filter);
    }

    public static List<DiplomaRegistradoEventResponse> getDiplomaRegistradoEvents(TransactionReceipt transactionReceipt) {
        List<DiplomaRegistradoEventResponse> responses = new ArrayList<>();

        // Itera sobre todos os logs do receipt
        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIPLOMAREGISTRADO_EVENT, log);

            // Se o log corresponde ao evento, adiciona à lista
            if (eventValues != null) {
                DiplomaRegistradoEventResponse typedResponse = new DiplomaRegistradoEventResponse();
                typedResponse.log = log;
                typedResponse.instituicao = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.numeroDiploma = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.hashDocumento = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.carimboCid = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.emitidoPor = (String) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.emitidoEm = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                responses.add(typedResponse);
            }
        }

        return responses;
    }


    public static DiplomaRegistradoEventResponse getDiplomaRegistradoEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DIPLOMAREGISTRADO_EVENT, log);
        DiplomaRegistradoEventResponse typedResponse = new DiplomaRegistradoEventResponse();
        typedResponse.log = log;
        typedResponse.instituicao = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.numeroDiploma = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.hashDocumento = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.carimboCid = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.emitidoPor = (String) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.emitidoEm = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
        return typedResponse;
    }

    public Flowable<DiplomaRegistradoEventResponse> diplomaRegistradoEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDiplomaRegistradoEventFromLog(log));
    }

    public Flowable<DiplomaRegistradoEventResponse> diplomaRegistradoEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIPLOMAREGISTRADO_EVENT));
        return diplomaRegistradoEventFlowable(filter);
    }

    public static List<RoleAdicionadaEventResponse> getRoleAdicionadaEvents(TransactionReceipt transactionReceipt) {
        List<RoleAdicionadaEventResponse> responses = new ArrayList<>();

        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEADICIONADA_EVENT, log);

            if (eventValues != null) {
                RoleAdicionadaEventResponse typedResponse = new RoleAdicionadaEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
                responses.add(typedResponse);
            }
        }

        return responses;
    }


    public static RoleAdicionadaEventResponse getRoleAdicionadaEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEADICIONADA_EVENT, log);
        RoleAdicionadaEventResponse typedResponse = new RoleAdicionadaEventResponse();
        typedResponse.log = log;
        typedResponse.role = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<RoleAdicionadaEventResponse> roleAdicionadaEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleAdicionadaEventFromLog(log));
    }

    public Flowable<RoleAdicionadaEventResponse> roleAdicionadaEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEADICIONADA_EVENT));
        return roleAdicionadaEventFlowable(filter);
    }

    public static List<RoleAdminChangedEventResponse> getRoleAdminChangedEvents(TransactionReceipt transactionReceipt) {
        List<RoleAdminChangedEventResponse> responses = new ArrayList<>();

        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEADMINCHANGED_EVENT, log);

            if (eventValues != null) {
                RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                responses.add(typedResponse);
            }
        }

        return responses;
    }


    public static RoleAdminChangedEventResponse getRoleAdminChangedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEADMINCHANGED_EVENT, log);
        RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
        typedResponse.log = log;
        typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleAdminChangedEventFromLog(log));
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEADMINCHANGED_EVENT));
        return roleAdminChangedEventFlowable(filter);
    }

    public static List<RoleGrantedEventResponse> getRoleGrantedEvents(TransactionReceipt transactionReceipt) {
        List<RoleGrantedEventResponse> responses = new ArrayList<>();

        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEGRANTED_EVENT, log);

            if (eventValues != null) {
                RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
                responses.add(typedResponse);
            }
        }

        return responses;
    }


    public static RoleGrantedEventResponse getRoleGrantedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEGRANTED_EVENT, log);
        RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
        typedResponse.log = log;
        typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleGrantedEventFromLog(log));
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEGRANTED_EVENT));
        return roleGrantedEventFlowable(filter);
    }

    public static List<RoleRemovidaEventResponse> getRoleRemovidaEvents(TransactionReceipt transactionReceipt) {
        List<RoleRemovidaEventResponse> responses = new ArrayList<>();

        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEREMOVIDA_EVENT, log);
            if (eventValues != null) {
                RoleRemovidaEventResponse typedResponse = new RoleRemovidaEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
                responses.add(typedResponse);
            }
        }

        return responses;
    }

    public static RoleRemovidaEventResponse getRoleRemovidaEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEREMOVIDA_EVENT, log);
        RoleRemovidaEventResponse typedResponse = new RoleRemovidaEventResponse();
        typedResponse.log = log;
        typedResponse.role = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<RoleRemovidaEventResponse> roleRemovidaEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleRemovidaEventFromLog(log));
    }

    public Flowable<RoleRemovidaEventResponse> roleRemovidaEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEREMOVIDA_EVENT));
        return roleRemovidaEventFlowable(filter);
    }

    public static List<RoleRevokedEventResponse> getRoleRevokedEvents(TransactionReceipt transactionReceipt) {
        List<RoleRevokedEventResponse> responses = new ArrayList<>();

        for (Log log : transactionReceipt.getLogs()) {
            Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEREVOKED_EVENT, log);
            if (eventValues != null) {
                RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
                typedResponse.log = log;
                typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
                responses.add(typedResponse);
            }
        }

        return responses;
    }


    public static RoleRevokedEventResponse getRoleRevokedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEREVOKED_EVENT, log);
        RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
        typedResponse.log = log;
        typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleRevokedEventFromLog(log));
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEREVOKED_EVENT));
        return roleRevokedEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> ADMIN_ROLE() {
        final Function function = new Function(FUNC_ADMIN_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> DEFAULT_ADMIN_ROLE() {
        final Function function = new Function(FUNC_DEFAULT_ADMIN_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> EMISSOR_ROLE() {
        final Function function = new Function(FUNC_EMISSOR_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> MINISTERIO_ROLE() {
        final Function function = new Function(FUNC_MINISTERIO_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> adicionarRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_ADICIONARROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> atualizarDiploma(byte[] numeroDiploma,
            byte[] novoHashDocumento, byte[] novoCarimboCid, byte[] novaAssinatura) {
        final Function function = new Function(
                FUNC_ATUALIZARDIPLOMA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(numeroDiploma), 
                new org.web3j.abi.datatypes.generated.Bytes32(novoHashDocumento), 
                new org.web3j.abi.datatypes.generated.Bytes32(novoCarimboCid), 
                new org.web3j.abi.datatypes.DynamicBytes(novaAssinatura)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> computeMessageHash(byte[] hashDocumento, byte[] carimboCid,
            byte[] numeroDiploma) {
        final Function function = new Function(FUNC_COMPUTEMESSAGEHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hashDocumento), 
                new org.web3j.abi.datatypes.generated.Bytes32(carimboCid), 
                new org.web3j.abi.datatypes.generated.Bytes32(numeroDiploma)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Tuple5<byte[], byte[], byte[], String, BigInteger>> consultarDiploma(
            String instituicao, byte[] numeroDiploma) {
        final Function function = new Function(FUNC_CONSULTARDIPLOMA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, instituicao), 
                new org.web3j.abi.datatypes.generated.Bytes32(numeroDiploma)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<byte[], byte[], byte[], String, BigInteger>>(function,
                new Callable<Tuple5<byte[], byte[], byte[], String, BigInteger>>() {
                    @Override
                    public Tuple5<byte[], byte[], byte[], String, BigInteger> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<byte[], byte[], byte[], String, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<byte[]> getRoleAdmin(byte[] role) {
        final Function function = new Function(FUNC_GETROLEADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> grantRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_GRANTROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> hasRole(byte[] role, String account) {
        final Function function = new Function(FUNC_HASROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> recoverSigner(byte[] messageHash, byte[] signature) {
        final Function function = new Function(FUNC_RECOVERSIGNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(messageHash), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registrarDiploma(byte[] numeroDiploma,
            byte[] hashDocumento, byte[] carimboCid, byte[] assinaturaInstituicao) {
        final Function function = new Function(
                FUNC_REGISTRARDIPLOMA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(numeroDiploma), 
                new org.web3j.abi.datatypes.generated.Bytes32(hashDocumento), 
                new org.web3j.abi.datatypes.generated.Bytes32(carimboCid), 
                new org.web3j.abi.datatypes.DynamicBytes(assinaturaInstituicao)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removerRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_REMOVERROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceRole(byte[] role,
            String callerConfirmation) {
        final Function function = new Function(
                FUNC_RENOUNCEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, callerConfirmation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_REVOKEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> verificarAssinatura(String instituicaoAddress,
            byte[] numeroDiploma) {
        final Function function = new Function(FUNC_VERIFICARASSINATURA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, instituicaoAddress), 
                new org.web3j.abi.datatypes.generated.Bytes32(numeroDiploma)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static DiplomaRegistry load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new DiplomaRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DiplomaRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DiplomaRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DiplomaRegistry load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new DiplomaRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DiplomaRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DiplomaRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DiplomaRegistry> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, admin)));
        return deployRemoteCall(DiplomaRegistry.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<DiplomaRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, admin)));
        return deployRemoteCall(DiplomaRegistry.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DiplomaRegistry> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, admin)));
        return deployRemoteCall(DiplomaRegistry.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DiplomaRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String admin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, admin)));
        return deployRemoteCall(DiplomaRegistry.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

   /* public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }*/

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class DiplomaAtualizadoEventResponse extends BaseEventResponse {
        public String instituicao;

        public byte[] numeroDiploma;

        public byte[] novoHash;

        public byte[] novoCarimboCid;

        public String atualizadoPor;

        public BigInteger atualizadoEm;
    }

    public static class DiplomaRegistradoEventResponse extends BaseEventResponse {
        public String instituicao;

        public byte[] numeroDiploma;

        public byte[] hashDocumento;

        public byte[] carimboCid;

        public String emitidoPor;

        public BigInteger emitidoEm;
    }

    public static class RoleAdicionadaEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;
    }

    public static class RoleAdminChangedEventResponse extends BaseEventResponse {
        public byte[] role;

        public byte[] previousAdminRole;

        public byte[] newAdminRole;
    }

    public static class RoleGrantedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }

    public static class RoleRemovidaEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;
    }

    public static class RoleRevokedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }
}
