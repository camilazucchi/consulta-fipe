package com.consultafipe.consulta.principal;

import com.consultafipe.consulta.model.*;
import com.consultafipe.consulta.service.ConsomeApi;
import com.consultafipe.consulta.service.Conversor;
import com.consultafipe.consulta.utility.ConversorInput;

import java.util.*;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsomeApi consomeApi = new ConsomeApi();
    private final Conversor conversor = new Conversor();
    private final ConversorInput normalizer = new ConversorInput();
    private String codigoMarca;
    private static final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private static final String MODELOS = "/modelos";
    private static final String ANOS = "/anos";
    private static final String CARROS = "carros/marcas";
    private static final String MOTOS = "motos/marcas";
    private static final String CAMINHOES = "caminhoes/marcas";

    public void mostraMenu() {
        System.out.println("""
                Olá, seja bem-vindo(a). Por favor, escolha uma das opções:
                - Carro
                - Moto
                - Caminhão
                Digite o nome do tipo de veículo que deseja realizar a busca:""");

        String tipoVeiculo = scanner.nextLine().toLowerCase();
        String normalizedInput = normalizer.normalizeString(tipoVeiculo);

        if (tipoVeiculo.isEmpty()) {
            System.out.println("É necessário digitar o nome do tipo de veículo para que possamos realizar a busca.");
        }

        switch (normalizedInput.toLowerCase(Locale.ROOT)) {
            case "carro":
                buscarVeiculo(CARROS);
                filtrarVeiculosPorMarca(CARROS);
                filtrarVeiculosPorNome(CARROS, codigoMarca);
                mostrarInformacoesVeiculos(CARROS, codigoMarca);
                break;
            case "moto":
                buscarVeiculo(MOTOS);
                filtrarVeiculosPorMarca(MOTOS);
                filtrarVeiculosPorNome(MOTOS, codigoMarca);
                mostrarInformacoesVeiculos(MOTOS, codigoMarca);
                break;
            case "caminhao":
                buscarVeiculo(CAMINHOES);
                filtrarVeiculosPorMarca(CAMINHOES);
                filtrarVeiculosPorNome(CAMINHOES, codigoMarca);
                mostrarInformacoesVeiculos(CAMINHOES, codigoMarca);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void buscarVeiculo(String tipoVeiculo) {
        String veiculoJson = consomeApi.pegaDados(ENDERECO + tipoVeiculo);
        List<Dados> dadosList = conversor.pegaDadosLista(veiculoJson, Dados.class);
        dadosList.sort(Comparator.comparingInt(dados -> Integer.parseInt(dados.codigo())));
        System.out.println("-- Marcas --");
        for (Dados dados : dadosList) {
            System.out.println(dados);
        }
    }

    public void filtrarVeiculosPorMarca(String tipoVeiculo) {
        System.out.println("Digite o código da marca que deseja buscar: ");
        codigoMarca = scanner.nextLine();

        String endereco = ENDERECO + tipoVeiculo + "/" + codigoMarca + MODELOS;
        String veiculoJson = consomeApi.pegaDados(endereco);
        var modeloLista = conversor.pegaDados(veiculoJson, Modelos.class);
        System.out.println("-- Veículos correspondentes com o código: " + codigoMarca + " --");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparingInt(modelo -> Integer.parseInt(modelo.codigo())))
                .forEach(System.out::println);
    }

    public void filtrarVeiculosPorNome(String tipoVeiculo, String codigoMarca) {
        System.out.println("Digite um trecho do nome de um veículo da lista acima: ");
        String nomeVeiculo = scanner.nextLine();
        String endereco = ENDERECO + tipoVeiculo + "/" + codigoMarca + MODELOS;
        String veiculoJson = consomeApi.pegaDados(endereco);
        var modeloLista = conversor.pegaDados(veiculoJson, Modelos.class);
        var veiculosFiltradosPorNome = modeloLista.modelos().stream()
                .filter(modelo -> modelo.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .sorted(Comparator.comparingInt(modelo -> Integer.parseInt(modelo.codigo())))
                .toList();
        System.out.println("-- Veículos filtrados pelo nome --");
        for (Dados veiculo : veiculosFiltradosPorNome) {
            System.out.println("- Código do veículo: " + veiculo.codigo() + " - Nome do veículo: " +
                    veiculo.nome().substring(0, 1).toUpperCase()
                    + veiculo.nome().substring(1).toLowerCase());
        }
    }

    public void mostrarInformacoesVeiculos(String tipoVeiculo, String codigoMarca) {
        System.out.println("Digite o código do modelo da lista acima: ");
        String codigoVeiculo = scanner.nextLine();

        if (codigoVeiculo.isEmpty()) {
            System.out.println("É necessário informar o código do veículo para realizar a busca.");
        }

        String endereco = ENDERECO + tipoVeiculo + "/" + codigoMarca + MODELOS + "/" + codigoVeiculo + ANOS;
        String veiculoJson = consomeApi.pegaDados(endereco);

        List<Dados> anos = conversor.pegaDadosLista(veiculoJson, Dados.class);
        List<Veiculo> veiculoList = new ArrayList<>();
        System.out.println("-- Informações gerais -- ");
        for (Dados dado : anos) {
            var enderecoAno = endereco + "/" + dado.codigo();
            try {
                veiculoJson = consomeApi.pegaDados(enderecoAno);
                Veiculo veiculo= conversor.pegaDados(veiculoJson, Veiculo.class);
                veiculoList.add(veiculo);
            } catch (Exception e) {
                System.err.println("Erro ao processar a requisição para o ano " + dado.codigo() + ": " + e.getMessage());
            }
        }

        veiculoList.forEach(System.out::println);
        System.out.println("Finalizando aplicação, obrigada por utilizar nossos serviços.");
    }

}
